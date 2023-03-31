package tl.azkom.treasury

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.*
import javax.sql.DataSource


@SpringBootApplication
class TreasuryApplication {
    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            println("Let's inspect the beans provided by Spring Boot:")
            val beanNames = ctx.beanDefinitionNames
            Arrays.sort(beanNames)
            for (beanName in beanNames) {
                println(beanName)
            }
        }
    }

    @Bean
    fun dataSource(): DataSource {
        val value = DriverManagerDataSource()
        value.setDriverClassName("org.postgresql.Driver")
        value.url = "jdbc:postgresql://localhost:5432/treasury"
        value.username = "postgres"
        value.password = "root"
        return value
    }

    @Bean
    fun jdbcTemplate(): JdbcTemplate = JdbcTemplate(dataSource())
}

fun main(args: Array<String>) {
    runApplication<TreasuryApplication>(*args)
}
