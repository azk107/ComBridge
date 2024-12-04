# Use Node.js LTS version
FROM node:18-slim

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY package*.json ./
RUN npm ci --only=production

# Bundle app source
COPY . .

# Expose port
EXPOSE 8080

# Start the application
CMD [ "npm", "start" ]