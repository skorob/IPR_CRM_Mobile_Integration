package com.ipr.iprcrm.integration.integrations.servicebus.converters

import com.google.common.base.Charsets
import com.google.common.io.Resources
import spock.lang.Specification

/**
 * Created by os on 8/21/2015.
 */
class AccountJsonToCRMMobileConverterTest extends Specification {
    def "Convert"() {
        setup:
        def accountGson = Resources.toString(Resources.getResource("json/Subscribe-Account.json"), Charsets.UTF_8)
        def converter = new JsonToCRMMobileModelConverter()
        when:
        def account = converter.convert(accountGson)
        then:

        account!=null
        account.header !=null
        account.body != null
        account.header.Timestamp.toGMTString() == "21 Aug 2015 08:12:30 GMT"
        account.header.Id == "CFFE9A9E-379B-46B0-BDA4-FE4A9FCED7DE"
        account.header.Source == "iprcrmmobile"
    }
}
