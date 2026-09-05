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
