package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.*;
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
        Integer statusId,
        Integer laboratorioId,
        Integer salaId
) {
    public Reserva mapearDadosParaEntidadeReserva(Usuario usuario, Status status, Laboratorio laboratorio, Sala sala) {
        Reserva reserva = new Reserva();
        reserva.setId(this.id);
        reserva.setDataInicio(this.dataInicio);
        reserva.setDataFim(this.dataFim);
        reserva.setHoraInicio(this.horaInicio);
        reserva.setHoraFim(this.horaFim);
        reserva.setUsuario(usuario);
        reserva.setStatus(status);
        reserva.setLaboratorio(laboratorio);
        reserva.setSala(sala);
        return reserva;
    }
}
