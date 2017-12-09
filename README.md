# API-Gateway

### Set environment variables
AWS_ACCESS_KEY_ID \
AWS_SECRET_ACCESS_KEY \
AWS_REGION


### Build docker image
./gradlew clean build buildDocker

### Run application
docker run -p 8080:8080 -t api-gateway