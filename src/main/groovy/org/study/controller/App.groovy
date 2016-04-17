package org.study.controller

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages=["org.study..*"])
class App {
    static void main(String[] args) {
        SpringApplication.run(App, args)
    }
}
