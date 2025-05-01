# Algoritmo IDA su IPFS


## Descrizione

Questo progetto implementa l’**Information Dispersal Algorithm** (IDA) di Rabin sulla rete **IPFS** (InterPlanetary File System) usando le API HTTP Java.  
L’obiettivo è sfruttare le caratteristiche di frammentazione e ridondanza di IDA per aumentare **disponibilità**, **efficienza** e **sicurezza** dei contenuti distribuiti su IPFS.

---

## Caratteristiche principali

- **Codifica**: frammentazione di un file in _n_ “frammenti IDA” partendo da _m_ blocchi, con matrice di codifica di tipo Vandermonde.  
- **Upload**: ogni frammento IDA è caricato singolarmente su IPFS via API HTTP, ottenendo un proprio CID.  
- **Salvataggio CID**: suggerita persistenza dei CID (file di mappatura o database).  
- **Decodifica**: recupero di qualsiasi _m_ frammenti per ricostruire il file originale con matrice di decodifica inversa.  
- **CLI Java**: interfaccia a riga di comando per:
  1. Selezionare file e parametri (_m_, _n_)  
  2. Codificare e caricare frammenti  
  3. Richiedere frammenti da IPFS e ricostruire il file  

---


## Prerequisiti

- Java 11+  
- Maven  
- Nodo IPFS in esecuzione (`ipfs daemon` su `localhost:5001`)  

---

## Struttura del progetto

```text
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/unict/idaipfs/
│   │   │   ├── Client.java          # CLI entry-point
│   │   │   ├── IDAEncoder.java      # codifica e generazione matrice Vandermonde
│   │   │   ├── IPFSService.java     # upload/download frammenti via API HTTP
│   │   │   └── IDADecoder.java      # decodifica e ricostruzione file
│   └── resources/
│       └── config.properties        # parametri default (host, porta IPFS)
└── LICENSE                          # licenza del progetto

