package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('calculate regular price')
        method('POST')
        url('/specials/1/calculate')
        headers {
            header('Accept', value(producer('application/json;charset=UTF-8'), consumer(regex('application/json.*'))))
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                unitPrice: 40.0,
                unitCount: 2
        ])
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                totalPrice: 80.0
        ])
    }
}
