package com.zaberp.zab.biwtabackend.controller;

<<<<<<< HEAD

import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.service.MmappointmentService;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.service.MmappointmentService;
import org.springframework.http.ResponseEntity;
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@RestController
@RequestMapping("/api/mmappointment")
public class MmappointmentController {
    @Autowired
    private MmappointmentService service;

    @GetMapping()
    public List<Mmappointment> findAll(
            @RequestParam("zid") int zid
    ) {
        return service.getAll(zid);
    }


//    api/appintment?zid=100000

//    api/mmappontment/100000/Case-00000000001

    @GetMapping("{zid}/{xcase}")
    public Mmappointment findByCase(
            @PathVariable("zid") int zid,
            @PathVariable("case") String xcase
    ){
        return service.getAllItems(zid,xcase);
    }
}
=======
    @RestController
    @RequestMapping("/api/mmappointments")
    public class MmappointmentController {

        private final MmappointmentService service;

        public MmappointmentController(MmappointmentService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<List<Mmappointment>> getAllAppointments() {
            return ResponseEntity.ok(service.findAll());
        }



        @PostMapping
        public ResponseEntity<Mmappointment> createAppointment(@RequestBody Mmappointment mmAppointment) {
            return ResponseEntity.ok(service.save(mmAppointment));
        }

        @PutMapping
        public ResponseEntity<Mmappointment> updateAppointment(@RequestBody Mmappointment mmAppointment) {
            return ResponseEntity.ok(service.save(mmAppointment));
        }

        @DeleteMapping("/{zid}/{xcase}")
        public ResponseEntity<Void> deleteAppointment(@PathVariable Integer zid, @PathVariable String xcase) {
            MmappointmentId id = new MmappointmentId();
            id.setZid(zid);
            id.setXcase(xcase);
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{zid}")
        public ResponseEntity<List<Mmappointment>> getAppointmentsByZid(@PathVariable int zid) {
            List<Mmappointment> appointments = service.findByZid(zid);
            return appointments.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(appointments);
        }

        @GetMapping("{zid}/{xcase}")
        public ResponseEntity<Mmappointment> getAppointmentByZidAndXcase(@PathVariable int zid, @PathVariable String xcase) {
            Mmappointment appointment = service.findByZidAndXcase(zid, xcase);
            return appointment != null ? ResponseEntity.ok(appointment) : ResponseEntity.notFound().build();
        }
    }

>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
