// create a byte array that will hold our document bytes
byte[] fileBytes = null;
String pathToDocument = "[PATH/TO/DOCUMENT]";

try
{
    String currentDir = System.getProperty("user.dir");
    
    // read file from a local directory
    Path path = Paths.get(currentDir + pathToDocument);
    fileBytes = Files.readAllBytes(path);
}
catch (IOException ioExcp)
{
    // TODO: handle error
    System.out.println("Exception: " + ioExcp);
    return;
}

// create an envelope that will store the document(s), field(s), and recipient(s)
EnvelopeDefinition envDef = new EnvelopeDefinition();
envDef.setEmailSubject("Please sign this document sent from Java SDK)");

Document doc = new Document();  
String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
doc.setDocumentBase64(base64Doc);
doc.setName("ShipmentFile"); // can be different from actual file name
doc.setFileExtension(".pdf");
doc.setDocumentId("1");

List<Document> docs = new ArrayList<Document>();
docs.add(doc);
envDef.setDocuments(docs);

Signer signer = new Signer();
signer.setEmail("{USER_EMAIL}");
signer.setName("{USER_FULLNAME}");
signer.setRecipientId("1");
signer.setClientUserId("1001");

SignHere signHere = new SignHere();
signHere.setDocumentId("1");
signHere.setPageNumber("1");
signHere.setRecipientId("1");
signHere.setXPosition("100");
signHere.setYPosition("150");

List<SignHere> signHereTabs = new ArrayList<SignHere>();      
signHereTabs.add(signHere);
Tabs tabs = new Tabs();
tabs.setSignHereTabs(signHereTabs);
signer.setTabs(tabs);

// add recipients (in this case a single signer) to the envelope
envDef.setRecipients(new Recipients());
envDef.getRecipients().setSigners(new ArrayList<Signer>());
envDef.getRecipients().getSigners().add(signer);

envDef.setStatus("sent");

try
{
    EnvelopesApi envelopesApi = new EnvelopesApi();
    // call the createEnvelope() API
    EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
    System.out.println("EnvelopeSummary: " + envelopeSummary);
}
catch (com.docusign.esign.client.ApiException ex)
{
    System.out.println("Exception: " + ex);
}
