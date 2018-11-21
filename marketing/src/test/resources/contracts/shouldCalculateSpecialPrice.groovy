package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('calculate special price')
        method('POST')
        url('/specials/1/calculate')
        headers {
            header('Accept', value(producer('application/json;charset=UTF-8'), consumer(regex('application/json.*'))))
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                unitPrice: 40.0,
                unitCount: 5
        ])
    }
    response {
        status 200
        body([
                specialId: value(producer(regex(alphaNumeric())), consumer('abc')),
                totalPrice: 150.0
        ])
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
