package s.s.Entitiy;

import jakarta.persistence.*;

@Entity
public class KeyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String message;
    private String phoneNumber;
    private String email;


    @Enumerated(EnumType.STRING)
    private keyStatus status;


    public KeyRequest() {
    }

    public KeyRequest(String message, String phoneNumber, String firstname, String lastname, String email, keyStatus status) {
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.status = status;
    }

    public enum keyStatus {
        AUFTRAG_ANGENOMMEN("Auftrag angenommen"),
        IN_BEARBEITUNG("Ist in Bearbeitung"),
        FERTIGGESTELLT("Wurde fertiggestellt");

        private final String status;

        keyStatus(String status) {
            this.status = status;
        }


        @Override
        public String toString() {
            return this.status;
        }
    }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public keyStatus getStatus() {
        return status;
        }
        public void setStatus(keyStatus status) {
        this.status = status;
        }


    }

