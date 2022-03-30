const sengridMail = require('@sendgrid/mail');
const fs = require('fs');
const ClientSideError = require('./errors/client_side_error');
const logger = require('logger-ort')(process.env.LOG_IMP);

sengridMail.setApiKey(process.env.SENDGRID_API_KEY);
sengridMail.setSubstitutionWrappers('-', '-');

async function send(to, template, templateOptions) {
  const templatePathPrefix = `${__dirname}/../templates/${template}`;

  const paths = {
    subject: `${templatePathPrefix}_subject.txt`,
    text: `${templatePathPrefix}.txt`,
    subject: `${templatePathPrefix}.html`,
  };

  if (!Object.keys(paths).every((key) => fs.existsSync(paths[key]))) {
    throw new ClientSideError(`Template named '${templatePathPrefix}' does not exist`);
  }

  const msg = {
    to,
    from: process.env.SENDGRID_SENDER_EMAIL,
    subject: fs.readFileSync(`${templatePathPrefix}_subject.txt`).toString(),
    text: fs.readFileSync(`${templatePathPrefix}.txt`).toString(),
    html: fs.readFileSync(`${templatePathPrefix}.html`).toString(),
    substitutions: templateOptions,
  };

  sengridMail.send(msg)
      .then(() => {
        logger.log('info', `sent email using ${template} to ${to}`);
      })
      .catch((error) => {
        logger.log('error', `Error sending email using ${template} to ${to}`);
      });
}

module.exports = {send};
