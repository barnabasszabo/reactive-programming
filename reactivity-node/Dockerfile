FROM keymetrics/pm2:latest-alpine

WORKDIR /opt/app

# Bundle APP files
COPY . .

# Install app dependencies
ENV NPM_CONFIG_LOGLEVEL warn

RUN npm install --production && npm run build

CMD [ "pm2-runtime", "start", "pm2.json" ]
