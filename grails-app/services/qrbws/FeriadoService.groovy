package qrbws

import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class FeriadoService {

    boolean eDomingo(Date dataDevolucao) {
        int diaDataDevolucao = Calendar.instance.with {
            time = dataDevolucao
            get(Calendar.DAY_OF_WEEK )
        }

        Calendar.SUNDAY == diaDataDevolucao
    }

    boolean existeFeriado(Date dataDevolucao) {
        Feriado.findAllByDataInicioGreaterThanAndDataFimLessThan(dataDevolucao, dataDevolucao)
    }

    def calcularDataDevolucao() {
        Date dataDevolucao = use(TimeCategory) { new Date() + 5.days }
        if (existeFeriado(dataDevolucao) || eDomingo(dataDevolucao)) {
            // TODO: Implementar
        } else {
            return dataDevolucao
        }
    }
}
