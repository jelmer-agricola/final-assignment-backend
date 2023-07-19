Volgorde:

1. models
2. Relaties

Part RepairCost lijkt standaard op 100.0 komen te staan dit ergens checken 




APPROVAL IN CARPART OF FF NADENKEN WAAR DAT WEL MOET IN INSPECTION SERVICE
[]approval van klant op andre plek na de carpartcheck en daar ook inschatting maken .
[] of na de carpartchcek --> klant bellen  of approval geven 
[]carparts en repairs automatisch koppelen 

UITGECOMMENT IN DATA.SQL de helft van carpartenum

User
toevoegen --> van pdf kan al voor security


3. Security
   //Todo als ik hiermee verder ga security toevoegen en user en role dus daaran tovoegen vanuit tech it easy


4. Testen
Kies makkelijke klasses 



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

