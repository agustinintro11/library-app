FROM node:16
WORKDIR /usr/src/app

ARG env
ENV ENV=${env}

# Bundle app source
COPY . .

RUN npm install

# For hot reload
RUN if [ "$env" = "DEV" ]; \
    then npm install -g nodemon; \
fi

EXPOSE 3001
CMD [ "npm", "start" ]

