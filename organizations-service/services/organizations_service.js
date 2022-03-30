const {Sequelize, sequelize} = require('../models');
const Organization = require('../models/organization')(sequelize, Sequelize);
const {NotUniqueError} = require('./errors/not_unique_error');
const {NotFoundError} = require('./errors/not_found_error');
const uuidv4 = require('uuid').v4;
const cache = require('../cache/cache');
const logger = require('logger-ort')(process.env.LOG_IMP);

function createApiKey(name) {
  return name + '.' + uuidv4().toString();
}

async function create(organizationCreateDTO) {
  logger.log('info', `Organization with values ${JSON.stringify(organizationCreateDTO)} is being created`);
  try {
    const organization = await Organization.create({
      name: organizationCreateDTO.organizationName,
      apiKey: createApiKey(organizationCreateDTO.organizationName),
    });

    return organization;
  } catch (e) {
    if (e.name == 'SequelizeUniqueConstraintError') {
      throw new NotUniqueError('Organization with this name already exists');
    }
    throw e;
  }
}

async function renewApiKey(apiKey) {
  const organization = await getOrganizationByApiKey(apiKey);

  logger.log('info', `Api key for organization ${organization.id} is being renewed`);

  const newKey = createApiKey(organization.name);
  const updatedOrganization = await Organization
      .update(
          {apiKey: newKey},
          {
            where: {id: organization.id},
            returning: true,
          },
      ).then((result) => {
        return result[1].pop();
      });

  cache.set(apiKey, null);
  cache.set(newKey, JSON.stringify(updatedOrganization));

  return updatedOrganization;
}

async function getOrganizationByApiKey(apiKey) {
  if (cache.exists(apiKey)) {
    return JSON.parse(cache.get(apiKey));
  }

  const organization = await Organization.findOne({where: {apiKey}});
  if (!organization) {
    throw new NotFoundError('Invalid api key');
  }
  cache.set(apiKey, JSON.stringify(organization));
  logger.log('info', `Adding api key for organization ${organization.id} to cache`);

  return organization;
}

async function getApiKey(name) {
  const organization = await Organization.findOne({where: {name}});
  if (!organization) {
    throw new NotFoundError(`Organization named ${name} does not exist`);
  }

  return organization.apiKey;
}

module.exports = {create, renewApiKey, getOrganizationByApiKey, getApiKey};

