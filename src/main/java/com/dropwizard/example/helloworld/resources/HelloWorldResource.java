package com.dropwizard.example.helloworld.resources;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import com.dropwizard.example.helloworld.api.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 这段代码是一个标准的JAX-RS资源类
 * GET请求指向“/hello-world” URL时会被调用的资源类
 * 
 * @author appleshan
 *
 */
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
 
    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }
 
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}
