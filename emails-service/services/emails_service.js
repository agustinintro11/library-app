const {send} = require(`./${process.env.EMAIL_IMP || 'sendgrid'}_emails_service`);

module.exports = {send};
