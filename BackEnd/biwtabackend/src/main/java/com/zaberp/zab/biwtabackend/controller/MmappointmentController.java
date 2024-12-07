package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.MmappointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/mmappointment")
    public class MmappointmentController extends BaseController<Mmappointment,MmappointmentId>{

        private final MmappointmentService service;

        public MmappointmentController(MmappointmentService service) {
            this.service = service;
        }



        @PostMapping
        public ResponseEntity<Mmappointment> createAppointment(@RequestBody Mmappointment mmAppointment) {
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



        @GetMapping("{zid}/{xcase}")
        public ResponseEntity<Mmappointment> getAppointmentByZidAndXcase(@PathVariable int zid, @PathVariable String xcase) {
            Mmappointment appointment = service.findByZidAndXcase(zid, xcase);
            return appointment != null ? ResponseEntity.ok(appointment) : ResponseEntity.notFound().build();
        }

    @Override
    protected CommonService<Mmappointment, MmappointmentId> getService() {
        return service;
    }
}


