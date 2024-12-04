FROM node:18-slim

WORKDIR /usr/src/app

# Copy package files
COPY package*.json ./

# Install production dependencies only
RUN npm install

# Copy app source
COPY . .

# Set environment variables
ENV NODE_ENV=production
ENV PORT=8080

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/health || exit 1

# Start the application
CMD ["node", "src/server.js"]