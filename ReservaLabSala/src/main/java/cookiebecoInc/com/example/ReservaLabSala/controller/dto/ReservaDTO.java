package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaDTO(
        Integer id,

        @NotNull(message = "Campo obrigatório")
        LocalDate dataInicio,

        @NotNull(message = "Campo obrigatório")
        LocalDate dataFim,

        @NotNull(message = "Campo obrigatório")
        LocalTime horaInicio,

        @NotNull(message = "Campo obrigatório")
        LocalTime horaFim,

        @NotNull(message = "Campo obrigatório")
        Integer usuarioId,

        @NotNull(message = "Campo obrigatório")
        Integer statusReservaId,

        Integer laboratorioId,

        Integer salaId
) {

    @AssertTrue(message = "Data Final precisa ser maior ou igual à Data Inicial")
    public boolean isDataFimValida() {
        if (dataInicio == null || dataFim == null) return true;
        return !dataFim.isBefore(dataInicio);
    }

    @AssertTrue(message = "A reserva é diária/por dia")
    public boolean isReservaDiaria() {
        if (dataInicio == null || dataFim == null) return true;
        return dataInicio.isEqual(dataFim);
    }

    @AssertTrue(message = "Hora Final precisa ser maior ou igual à Hora Inicial")
    public boolean isHoraFimValida() {
        if (horaInicio == null || horaFim == null) return true;
        return horaFim.isAfter(horaInicio);
    }

    @AssertTrue(message = "Informe exatamente um recurso: Laboratório ou Sala")
    public boolean isRecursoValido() {
        return (laboratorioId != null && salaId == null) || (laboratorioId == null && salaId != null);
    }

    public Reserva mapearDadosParaEntidadeReserva() {
        Reserva reserva = new Reserva();
        reserva.setId(this.id);
        reserva.setDataInicio(this.dataInicio);
        reserva.setDataFim(this.dataFim);
        reserva.setHoraInicio(this.horaInicio);
        reserva.setHoraFim(this.horaFim);

        if (this.usuarioId != null) {
            Usuario usuario = new Usuario();
            usuario.setId(this.usuarioId);
            reserva.setUsuario(usuario);
        }

        if (this.statusReservaId != null) {
            StatusReserva status = new StatusReserva();
            status.setId(this.statusReservaId);
            reserva.setStatusReserva(status);
        }

        if (this.laboratorioId != null) {
            Laboratorio lab = new Laboratorio();
            lab.setId(this.laboratorioId);
            reserva.setLaboratorio(lab);
        }

        if (this.salaId != null) {
            Sala sala = new Sala();
            sala.setId(this.salaId);
            reserva.setSala(sala);
        }

        return reserva;
    }
}
