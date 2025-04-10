package com.dts.jenkinsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JenkinsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenkinsDemoApplication.class, args);
    }

    //sudo firewall-cmd --add-port=8084/tcp --permanent
    //sudo firewall-cmd --reload


//    login to harbor: docker login 103.226.249.128:8142 -u admin -p Harbor12345

//    phân quyền jenkins để chạy được docker:
//    sudo usermod -aG docker jenkins
//    sudo systemctl restart jenkins


//    docker build --platform linux/amd64 -t jenkins-demo:latest .
//    docker tag jenkins-demo:latest 103.226.249.128:8142/demo/jenkins-demo:latest
//    docker push 103.226.249.128:8142/demo/jenkins-demo:latest

// run docker image
    /*
      -t : terminal ảo, in log ra terminal
      -d : chạy ngầm dạng deamon
      -p : publish mapping port từ docker ra ngoài
       --name : đặt tên cho container
       ví dụ câu lệnh hoàn chỉnh
     */
//    docker run  -t -d  -p 8083:8083 --name jenkins-demo-container  jenkins-demo

//    stop docker container
//    docker stop <containerName>

//    log: docker logs -f --tail 200 <containerName>
}
