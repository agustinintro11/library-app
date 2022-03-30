FROM node:16
WORKDIR /usr/src/app

ARG env
ENV ENV=${env}

COPY package*.json ./
RUN npm install

# For hot reload
RUN if [ "$env" = "DEV" ]; \
    then npm install -g nodemon; \
fi

# Bundle app source
COPY . .

EXPOSE 3002
CMD [ "npm", "start" ]

