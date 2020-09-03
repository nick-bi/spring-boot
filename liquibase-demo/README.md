### springboot liquibase db migrate demo

### add liquibase depend
```
   <dependency>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-core</artifactId>
        </dependency>
 ```
 
 ## add ``db.changelog-master.yaml`` file in '/reources/db/changelog',suggest include change file， not include change log detail in it
 
 ```
 databaseChangeLog:
  - include:
      file: db/changelog/init.sql
  - include:
      file: db/changelog/create_table.sql

```
