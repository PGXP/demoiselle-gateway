# demoiselle-gateway
API Gateway para projeto JEE7 Demoiselle

### REPO Maven
```JAVA
<repository>
   <id>demoiselle-contrib-mvn-repo</id>
      <url>https://raw.githubusercontent.com/PGXP/demoiselle-gateway/master/mvn-repo/</url>
          <snapshots>
              <enabled>true</enabled>
              <updatePolicy>always</updatePolicy>
          </snapshots>
</repository>
```

### REPO persistence.xml
#### Postgresql

```JAVA
<persistence-unit name="gatewayPU" transaction-type="JTA">
	<provider>org.hibernate.ejb.HibernatePersistence</provider>
	<jta-data-source>java:jboss/datasources/GatewayDS</jta-data-source>
	<class>org.demoiselle.jee.gateway.entity.Client</class>
	<class>org.demoiselle.jee.gateway.entity.Hit</class>
	<class>org.demoiselle.jee.gateway.entity.Resume</class>
	<exclude-unlisted-classes>true</exclude-unlisted-classes>
	<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
	<validation-mode>NONE</validation-mode>
	<properties>
	    <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
	    <property name="hibernate.show_sql" value="false"/>
	    <property name="hibernate.cache.use_second_level_cache" value="true"/>
	    <property name="hibernate.cache.use_query_cache" value="true"/>
	</properties>
</persistence-unit>
```
#### Memória

```JAVA
<persistence-unit name="gatewayPU" transaction-type="JTA">
	<provider>org.hibernate.ejb.HibernatePersistence</provider>
	<jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>
	<class>org.demoiselle.jee.gateway.entity.Client</class>
	<class>org.demoiselle.jee.gateway.entity.Hit</class>
	<class>org.demoiselle.jee.gateway.entity.Resume</class>
	<exclude-unlisted-classes>true</exclude-unlisted-classes>
	<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
	<validation-mode>NONE</validation-mode>
	<properties>
	    <property name="hibernate.dialect" value="org.hibernate.dialect.HSQLDialect"/>
	    <property name="hibernate.show_sql" value="false"/>
	    <property name="hibernate.cache.use_second_level_cache" value="true"/>
	    <property name="hibernate.cache.use_query_cache" value="true"/>
	    <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
	</properties>
</persistence-unit>
```
