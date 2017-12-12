# API-Gateway

### Set environment variables
AWS_S3_ACCESS_KEY_ID \
AWS_S3_SECRET_ACCCESS_KEY \
AWS_S3_BUCKET \
AWS_QUEUE_NAME \
AWS_QUEUE_ACCESS_KEY_ID \
AWS_QUEUE_SECRET \
AWS_REGION \
SPRING_DATASOURCE_URL \
SPRING_DATASOURCE_USERNAME \
SPRING_DATASOURCE_PASSWORD


### Build docker image
./gradlew clean build buildDocker

### Run application
docker run -p 8080:8080 -t api-gateway