FROM node:18.12.1 as node
WORKDIR /app

COPY . .

RUN npm install
RUN npm run build --prod

FROM nginx:alpine
COPY --from=node /app/dist/ui /usr/share/nginx/html
