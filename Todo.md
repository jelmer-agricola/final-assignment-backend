Volgorde:

1. models
2. Relaties

of  in repair of in inspection terugzien  wat er gerepareerd is.




-[ ] waar de relaties te zien zijn in pgadmin kijken hoe die gevuld kunnen worden
 Carpparts
-[x] car_licenceplate
- CarPartStatusCheck --> hier kan de status aangepast worden. Als een car wordt
- gemaakt dan worden de carparts er automatisch aan toegevoegd.


Repair
Zoeken van repairs op licenseplate gelukt!! 
als je iets toevoegd aan een 
Krijg bij zoeken van repairs geen carpart mee als je repairs get op licenseplate


ook steeds checken in de dtos; of deze dingen worden doorgevoer.d
-[x]carpart_id --> je hebt de auto en daarin geef je het onderdeel aan. 
-[x]inspection_id

Inspection
-[X]car_licenseplate
-[ ]

Invoice
-[]car_licenseplate
-[]inspection_id

-[] kijken welke crud operations e.d. er weg kunnen.P

User


3. Security
   //Todo als ik hiermee verder ga security toevoegen en user en role dus daaran tovoegen vanuit tech it easy

4. file uploaden

instock verplaatsen naar repair of inspection

Als ik een repair approved of dat soort dingen heb dan moet dat in het systeem aangegeven kunnen worden

-[x] add owners to sql

### Inspection

- [x] Alle request nalopen
- [x] models
- [x] 
-
- ### Car
- [x] owner doorvoeren
- [x] Alle reques t nalopen
- [x] models
- [x] Crud

### Invoice

- [x] Alle request nalopen
- [x] models
- [x] Crud

### Repair

- [x] Alle request nalopen
- [x] models
- [x] Crud
- [ ] update en delete maken voor repair

### Authority

- [ ] Alle request nalopen
- [ ] models
- [ ] Crud

### User

- [ ] Alle request nalopen
- [ ] models
- [ ] Crud

test schrijven voor makkelijke klasse.

users niet testen en security ook niet

**Finished**

- [x] kenteken als id gebruiken voor car
  BEGINNENN MET create operations en post request
-[x] Recordnotfound exception (iets wordt gevraagd wat niet bestaat )
-[x] duplicateError voor --> de auto die al bestaat  "licenseplate Already Exists"


In Television controller
@PutMapping("/{id}/rc/{rc_id}")
public ResponseEntity<TelevisionOutputDto> assignRemoteToTelevision(@PathVariable Long id, @PathVariable Long rc_id) {
return ResponseEntity.ok(televisionService.assignRemoteToTelevision(id, rc_id));
}

public TelevisionOutputDto assignRemoteToTelevision(Long id, Long rc_id) throws RecordNotFoundException {
Optional<Television> optionalTelevision = televisionRepository.findById(id);
Optional<RC> optionalRC = rcRepository.findById(rc_id);
if (optionalTelevision.isEmpty() && optionalRC.isEmpty()) {
throw new RecordNotFoundException("Remote or television with" + rc_id + " and " + id + "does not exist");
}
Television television = optionalTelevision.get();
RC rc = optionalRC.get();
television.setRc(rc);
Television updateTelevision = televisionRepository.save(television);
return transferTelevisionModelToOutputDto(updateTelevision);
}

