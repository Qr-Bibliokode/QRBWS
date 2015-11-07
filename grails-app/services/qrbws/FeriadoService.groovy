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

    Feriado existeFeriado(Date dataDevolucao) {
        List<Feriado> feriado = Feriado.findAllByDataInicioLessThanAndDataFimGreaterThan(dataDevolucao, dataDevolucao)
        feriado ? feriado.first() : null
    }

    Date calcularDataDevolucao(int days = 5, Date dataDevolucao = null) {
        use(TimeCategory) { dataDevolucao = (dataDevolucao ?: new Date()) + days.days }
        Feriado feriado = existeFeriado(dataDevolucao)
        if (feriado) {
            dataDevolucao = calcularDataDevolucao(1, feriado.dataFim)
        } else if (eDomingo(dataDevolucao)) {
            dataDevolucao = calcularDataDevolucao(1, dataDevolucao)
        } else {
            return dataDevolucao
        }
    }

    Feriado validaFeriado(Feriado feriado) {
        if (feriado.dataFim.before(feriado.dataInicio)) {
            feriado.errors.reject('feriado.data.fim.anterior.data.inicio')
            return feriado
        }
    }
}