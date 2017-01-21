package com.dropwizard.example.helloworld;

import com.dropwizard.example.helloworld.health.TemplateHealthCheck;
import com.dropwizard.example.helloworld.resources.HelloWorldResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
	
	// Application 的入口点的 main 方法
	public static void main(String[] args) throws Exception {
		// new HelloWorldApplication().run(args);
		
		// server 命令: 将启动嵌入式 Jetty 服务器
		new HelloWorldApplication().run(new String[] { "server", "hello-world.yml" });
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	// 初始化方法在 Application 运行方法之前被调用。
	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// nothing to do yet
	}

	// Application 运行时将调用它的 run 方法，
	@Override
	public void run(HelloWorldConfiguration configuration, Environment environment) {
		final HelloWorldResource resource = new HelloWorldResource(
	        configuration.getTemplate(),
	        configuration.getDefaultName()
	    );
	    final TemplateHealthCheck healthCheck =
	        new TemplateHealthCheck(configuration.getTemplate());
	    environment.healthChecks().register("template", healthCheck);
	    environment.jersey().register(resource);
	}

}
