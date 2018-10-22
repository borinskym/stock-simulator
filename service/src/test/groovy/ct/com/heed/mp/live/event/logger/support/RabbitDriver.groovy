package ct.com.heed.mp.live.event.logger.support

import org.springframework.amqp.AmqpIOException
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitDriver {


    private AbstractConnectionFactory connectionFactory
    private RabbitAdmin rabbitAdmin
    private RabbitTemplate rabbitTemplate
    private List<Message> messages = new LinkedList<Message>()
    private String exchange

    RabbitDriver(exchangeName) {
        exchange = exchangeName
        connectionFactory = initializeConnectionFactory()
        rabbitAdmin = new RabbitAdmin(connectionFactory)
        rabbitTemplate = new RabbitTemplate(connectionFactory)
        rabbitTemplate
    }

    static AbstractConnectionFactory initializeConnectionFactory() {
        def inferHost = {
            Optional.ofNullable(System.getenv("RABBITMQ_HOST"))
                    .orElse("localhost")
        }

        AbstractConnectionFactory connectionHandlerFactory = new CachingConnectionFactory()
        connectionHandlerFactory.setHost(inferHost())
        connectionHandlerFactory.setVirtualHost("/")
        connectionHandlerFactory.setUsername("guest")
        connectionHandlerFactory.setPassword("guest")
        connectionHandlerFactory
    }

    static RabbitDriver newExchange(String exchangeName) {
        try {
            def driver = new RabbitDriver()
            driver.exposeExchange(exchangeName)
            driver.purge()
            driver
        } catch (AmqpIOException e) {
            println("error in creating exchangeName: ${e.getMessage()}")
            System.exit(1)
        }
    }

    void exposeExchange(String exchange) {
        rabbitAdmin.declareExchange(new TopicExchange(exchange))
        rabbitTemplate.setExchange(exchange)
    }


    void purge() {
        messages.removeAll()
    }

    void sendTrigger(String message, String routingKey) {
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, message)
        }catch (Exception e){
            e.printStackTrace()
            print(e)
        }

    }
}
