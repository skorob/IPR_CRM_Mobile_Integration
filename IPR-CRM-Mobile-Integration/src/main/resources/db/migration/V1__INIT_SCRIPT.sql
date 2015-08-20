CREATE SEQUENCE hibernate_sequence MINVALUE 1 MAXVALUE 100000000000000 INCREMENT BY 1 START WITH 101376 CACHE 20 NOORDER NOCYCLE ;

create table PROPERTY  (
    KEY varchar(100) not null,
    VALUE varchar(400) not null,
    DESCRIPTION varchar(1000),
    PROPERTY_TYPE VARCHAR(50) not null,
    CONSTRAINT PROPERTY_PK PRIMARY KEY (KEY)
);

create table ADDRESS (
    ID NUMBER(19,0) NOT NULL,
    STREET varchar(100),
    CITY varchar(100),
    ZIP varchar(50),
    COUNTRY varchar(50),
    CONSTRAINT ADDRESS_PK PRIMARY KEY (ID)
);

create table CUSTOMER  (
    ID NUMBER(19,0) NOT NULL,
    FIRST_NAME varchar(100) not null,
    LAST_NAME varchar(100) not null,
    DELIVERY_ADDRESS_ID NUMBER(19,0) not null,
    LANGUAGE varchar(50),
    CONSTRAINT CUSTOMER_PK PRIMARY KEY (ID),
    CONSTRAINT  CUSTOMER_FK FOREIGN KEY (DELIVERY_ADDRESS_ID) REFERENCES ADDRESS(ID)
);

create table ORDER_DETAIL  (
    ID varchar(100) not null,
    TYPE varchar(100) not null,
    STATUS varchar(100) not null,
    CUSTOMER_ID NUMBER(19,0) not null,
    CORRELATION_ID varchar(100) not null,
    CONSTRAINT ORDER_DETAIL_PK PRIMARY KEY (ID),
    CONSTRAINT ORDER_DETAIL_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID)
);

CREATE INDEX ORDER_DETAIL_CORR_ID_IDX ON ORDER_DETAIL (CORRELATION_ID);

create table ORDER_ITEM  (
    ID NUMBER(19,0) NOT NULL,
    PRODUCT_TYPE varchar(100) not null,
    PRODUCT_ID varchar(100) not null,
    ORDER_ID varchar(100) not null,
    ACTIVATION_TYPE varchar(100),
    MSISDN varchar(100),
    DOMAIN varchar(50),
    CONSTRAINT ORDER_ITEM_PK PRIMARY KEY (ID),
    CONSTRAINT  ORDER_ITEM_FK  FOREIGN KEY (ORDER_ID) REFERENCES ORDER_DETAIL(ID)
);


INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('PROCESS_SCAN_PACKAGE','com.tele2.ooe.core.process','Base package for scanning processes on boot','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('AZURE_ENDPOINT','amqps://roots-test-accesskey:F34m82ZvDhTaQ9HxbVu5qXl5ebD9v185TjZ28Hbi9jg=@roots-test-ns.servicebus.windows.net','Azure connection string','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('AZURE_EVENT_TOPIC','roots-eventtopic','Topic for processing events','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('AZURE_ORDER_TOPIC','roots-ordertopic','Topic for processing orders','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('AZURE_SUBSCRIPTION','provengsub','Azure subscription definition','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('ELASTIC_SEARCH_CLUSTER','elastic_search_test','Elastic search cluster name','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('SISTEERS_ENDPOINT','amqps://roots-test-accesskey:FWEckLX8sSNCej9VGzEuMif7EXsr957UqT3f0ZFXbA0=@roots-test.servicebus.windows.net','Sisteer connection string','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('SISTEERS_REQUEST_QUEUE','roots-subscriptionrequestqueue','Sisteer request provisioning queue','STRING');
INSERT INTO PROPERTY (KEY, VALUE, DESCRIPTION, PROPERTY_TYPE) VALUES ('SISTEERS_RESPONSE_QUEUE','roots-subscriptionresponsequeue','Sisteer response provisioning queue','STRING');

