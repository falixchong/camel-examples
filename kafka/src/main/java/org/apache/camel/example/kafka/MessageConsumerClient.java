/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.camel.example.kafka.MessagePublisherClient.setUpKafkaComponent;

public final class MessageConsumerClient {

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumerClient.class);

    private MessageConsumerClient() {
    }

    public static void main(String[] args) throws Exception {

        LOG.info("About to run Kafka-camel integration...");

        try (CamelContext camelContext = new DefaultCamelContext()) {

            LOG.info("About to start route: Kafka Server -> Log ");
            // Set the location of the configuration
            camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
            // Set up the Kafka component
            setUpKafkaComponent(camelContext);
            // Add route to send messages to Kafka

            camelContext.addRoutes(createRouteBuilder());
            camelContext.start();

            // let it run for 5 minutes before shutting down
            Thread.sleep(5L * 60 * 1000);
        }
    }

    // static RouteBuilder createRouteBuilder() {
    //     return new RouteBuilder() {
    //         public void configure() {
    //         from("kafka:{{consumer.topic}}"
    //                 + "?maxPollRecords={{consumer.maxPollRecords}}"
    //                 + "&consumersCount={{consumer.consumersCount}}"
    //                 + "&seekTo={{consumer.seekTo}}"
    //                 + "&groupId={{consumer.group}}")
    //                 .routeId("FromKafka")
    //                 .log("${body}");
                    
    //         }
    //     };
    // }

    static String saslJaasConfig = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"{{consumer.scamUsername}}\" password=\"{{consumer.scamPassword}}\";";

    static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
            from("kafka:{{consumer.topic}}?"
                + "brokers={{kafka.brokers}}"
                + "&saslMechanism={{consumer.saslMechanism}}"  
                + "&securityProtocol={{consumer.securityProtocol}}"
                + "&sslEndpointAlgorithm={{consumer.sslEndpointAlgorithm}}"
                + "&saslJaasConfig=" + saslJaasConfig
                + "&sslEnabledProtocols={{consumer.sslEnabledProtocols}}"
                + "&sslTruststoreType={{consumer.sslTruststoreType}}"
                + "&sslTruststoreLocation={{consumer.sslTruststoreLocation}}"
                + "&sslTruststorePassword={{consumer.sslTruststorePassword}}"
                + "&clientId={{consumer.clientId}}"
                + "&groupId={{consumer.groupId}}")
                .routeId("FromKafka")
                .log("${body}");   
            }
        };
    }
}
