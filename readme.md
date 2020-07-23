DOCKER COMMANDS




Basic Docker Commands


docker run -dit openjdk:8-jdk-alpine - to run new image into local machine

docker container ls - list of containers

docker images ls - list of images

docker images - list of images on local machine

docker container exec romantic_johnson rm  /tmp/boot-docker-1.0.jar - to      delete jar from tmp folder of container

docker system prune -a - to remove all stopped container and all unused images

docker container stop 3f8 - to stop container
3f8 - is docker id

docker image rm a35 - to delete image from local machine

Docker container rm 1f6 - to remove container from local machine








Run a new image into docker container (Manual Process)

docker container exec romantic_johnson ls /tmp - to check container folders

docker container cp boot-docker-1.0.jar romantic_johnson:/tmp - to copy boot-docker-1.0.jar file into docker container of tmp folder

docker container commit romantic_johnson doc/spring-boot:manual1 - to save container as an image

docker run doc/spring-boot:manaual1 - to run docker container
doc/spring-boot - name of repository
manaual1 - name of tag
//Above command will not run jar in docker. Because we just start the container. We need to run container as jar has need to be launched at startup. So whenever container start run jar file automatically will start.

docker container commit --change="CMD ("java" "-jar" "/tmp/boot-docker-1.0.jar")" romantic_johnson doc/spring-boot:manual3 - To start jar as startup in container.

 docker run -p 8080:8080 doc/spring-boot:manual2 - to run jar in container












Run a new image into docker container (Automatic Process)

First create Dockerfile in project root folder by write following commands in Dockerfile :
	FROM openjdk:8-jdk-alpine
ADD target/boot-docker-1.0.jar boot-docker-1.0.jar
ENTRYPOINT ["sh","-c","java -jar /boot-docker-1.0.jar"]

docker build -t boot-starter-api:myfile . - to run Dockerfile from spring boot project. Execute this command in the directory of Dockerfile. From above command image will create and it will also tag the image.

docker run -p 8080:8080 boot-starter-api:myfile - to run image to create container.













Creating image without any docker command (Fully Automatic)

First Add following plugin into project pom.xml file:

<plugin>
   <groupId>com.spotify</groupId>
   <artifactId>dockerfile-maven-plugin</artifactId>
   <version>1.4.10</version>
   <executions>
       <execution>
           <id>default</id>
           <goals>
               <goal>build</goal>
           </goals>
       </execution>
   </executions>
   <configuration>
       <repository>${project.name}</repository> //Image name
       <tag>${project.version}</tag>		  //tag name	
       <skipDockerInfo>true</skipDockerInfo>
   </configuration>
</plugin>
	
	After adding above plugin in pom file, docker build becomes part of       maven build. So now need to create image externally, it will automatically create during maven build.

After adding above plugin run following command to run image into container
docker run -p 8080:8080 boot-starter-api:1.0







For Pushing image on docker hub

First we have to create repository at docker hub.

After creating repository use following commands to push local image to docker hub
docker tag boot-starter-api:myfile jbirtharia/docker-basic:myfile - to tag your local image to docker-hub repository
docker push jbirtharia/docker-basic:myfile - to push local image into remote repository on docker-hub

Docker uses cache to execute commands if the container does not have changes. If container has changes then it will not use cache and execute commands. 







Installation of MYSQL Image on Local (MYSQL Container)

docker run mysql:5.7 - to run mysql container as image. 5.7 is the version of image. If an image is not found on the container, then it will download from dockerhub.

There are few commands to execute images :
docker run --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=todos --name mysql -p 3307:3306 mysql:5.7
By above command database is create through root user
docker run -d --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=todos --name mysql -p 3307:3306 mysql:5.7
By above command image will run in detached mode
docker run -d --env MYSQL_ROOT_PASSWORD=root --env MYSQL_USER=uat --env MYSQL_PASSWORD=uat --env MYSQL_DATABASE=todos --name mysql -p 3307:3306 mysql:5.7
By above command firs new user (i.e. uat) is created with password (i.e. uat) and then a new table is created.
      

NOTE :  1. Never create a new user with ‘root’ name. 
	   2. Use below command to connect container sql through cmd :
mysql -h localhost -P 3307 -u root -p 













Connection of App Container to MYSQL Container

Since both container are different and internally docker using bridge network so from that existing network two container will never connect.
So first we have to create a new network by using following command
docker network create web-application-mysql-network

Now we have to create mysql container with using above network. So we have to execute following command to create mysql container
 docker run --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=todos --name mysql -p 3307:3306 --network=web-application-mysql-network mysql:latest

Since windows mysql is already running on 3306, so i have used 3307 port to expose the port, so i can connect the mysql container from local also.
But please use port number - 3306 in application.property file because on container it is running on 3306 and our app container also connect database on 3306 port number.

Now we have to create App container by connecting through our network. We will use below command 
docker container run -p 80:8080 --network=web-application-mysql-network --env RDS_HOSTNAME=mysql jayendra/todo-web-application-mysql:1.0






Attaching of Docker Volume to persist MYSQL data

When we run mysql container then by restarting that container our data will be lost because there is no local volume attach to container where data can persist. So for that we have to attach local volume to container where mysql can persist their data.
So when our container restart or we have to create new container our old records will not be lost it will remain as it is in local volume (i.e. local machine disk).

So for achieving above condition we have to attach local volume with container. So when we are creating mysql container we to pass local volume path in command 

docker run --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=todos --name mysql -p 3307:3306 --network=web-application-mysql-network --volume mysql-database-volume:/var/lib/mysql mysql:latest

--volume mysql-database-volume:/var/lib/mysql - command for  attaching local volume with container. 

After this we can start App container by following command :

docker container run -p 80:8080 --network=web-application-mysql-network --env RDS_HOSTNAME=mysql jayendra/todo-web-application-mysql:1.0


