package qrbws

import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class FeriadoService {

    boolean eDomingo(Date dataDevolucao) {
        int diaDataDevolucao = Calendar.instance.with {
            time = dataDevolucao
            get(Calendar.DAY_OF_WEEK)
        }
        Calendar.SUNDAY == diaDataDevolucao
    }

    boolean existeFeriado(Date dataDevolucao) {
        Feriado.findAllByDataInicioGreaterThanAndDataFimLessThan(dataDevolucao, dataDevolucao)
    }

    Date calcularDataDevolucao(Date dataDevolucao = null) {
        dataDevolucao = use(TimeCategory) { new Date() + 5.days }
        if (existeFeriado(dataDevolucao) || eDomingo(dataDevolucao)) {
            dataDevolucao = use(TimeCategory) { dataDevolucao + 1.days }
            calcularDataDevolucao()
        } else {
            return dataDevolucao
        }
    }
}
