# Onion Architecture example Application

## Fachlichkeit

Warenkorb 
- Kann Elemente aufnehmen
- Anzahl der Elemente kann geändert werden
- Preis kann berechnet werden
- Elemente können entfernt werden

Warenkorbelement
- Kann am Namen identifiziert werden
- Kennt seine Quantität
- Kennt seine maximale Quantität
- kann den eigenen Preis (inklusive Quantität) berechnen

Use Cases:

0 Warenkorb anlegen, löschen  
0.1 Elemente dem Warenkorb, hinzufügen, entfernen, Menge ändern  
0.2 Warenkorb ansehen  
1 Preis des Warenkorb berechnen  
1.1 Rechnung schreiben  
2 Warenkorbelemente auflisten  

## Implementierung
Nach Onion Modell: [docs](docs/190313_OnionArchitecturesAndStereotypes.pdf)

## Wann-Das? Was ist das? Ein paar Faustregeln für DDD Begriffe

### Domäne

#### Entity
Stateful Object (Irgendwas, was mehr als die Request überlebt und ein Recht auf eine eigene ID hat), wird benötigt

#### Value Object
Stateless Object (Irgendwas fachliches, aber es hat keine ID verdient)

#### Aggregate
Ich habe ein paar Dinge zusammengefasst an denen Ich den UseCase aus dem Hut ziehe.
Zum Beispiel eine Vorgangsnummer.

#### Aggregate Factory
Ich muss ein Aggregat erzeugen.

#### Repository
Ich will ein Element speichern oder laden

#### Domain Event
Ich würde gerne was fachliches machen, aber ein anderes System (eine andere Fachlichkeit) muss mir helfen oder Bescheid wissen.

#### Domain Event Publisher
Schmeißt das Domain event dann auch zum System, welches wir brauchen.....

#### Infrastructure Service
Braucht man nicht so oft: Wir müssen ein technisches System unbedingt aus der Fachlichkeit ansprechen und weder DomainEventPublisher noch Repository waren ein treffender Begriff. 

#### Domain Event Handler
Ein anderes System braucht was von uns und es ist ein ach so komplizierter Vorgang. Frisst DomainEvents.

#### DomainService
Ich mache Dinge die mehr als ein Aggregat oder Entity betreffen. Ich mache Dinge die nur ein Aggregat/Entity betreffen, aber weigere mich das Repo im ApplicationService zu halten.

### Application 
#### Application Service
Bekommt Sachen von der Infrastruktur und schiebt sie in die Domäne (ggf. mit Mappings und so). Darf auch für die Domäne das Laden und Speichern aus dem DomainRepository übernehmen.
