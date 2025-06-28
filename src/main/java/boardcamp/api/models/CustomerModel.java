package boardcamp.api.models;

import boardcamp.api.dtos.CustomerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(value = 10)
    @Max(value = 11)
    private String phone;

    @Column(nullable = false)
    @Size(min = 11, max = 11)
    private String cpf;

    public CustomerModel(CustomerDTO dto){
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.cpf = dto.getCpf();
    }
}
