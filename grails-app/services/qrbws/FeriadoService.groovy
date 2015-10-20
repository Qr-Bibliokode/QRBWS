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

    Date calcularDataDevolucao(int days = 5, Date dataDevolucao = null) {
        use(TimeCategory) { dataDevolucao = (dataDevolucao ?: new Date()) + days.days }
        if (existeFeriado(dataDevolucao) || eDomingo(dataDevolucao)) {
            dataDevolucao = calcularDataDevolucao(1, dataDevolucao)
        } else {
            return dataDevolucao
        }
    }
}
