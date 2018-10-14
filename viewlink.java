RecipientViewRequest viewOptions = new RecipientViewRequest()
            {
                ReturnUrl = "https://www.docusign.com/",
                ClientUserId = "1001",  // must match clientUserId of the embedded recipient
                AuthenticationMethod = "email",
                UserName = "{USER_NAME}",
                Email = "{USER_EMAIL}"
            };

            // instantiate an envelopesApi object
            EnvelopesApi envelopesApi = new EnvelopesApi();

            // create the recipient view (aka signing URL)
            ViewUrl recipientView = envelopesApi.CreateRecipientView(accountId, envelopeId, viewOptions);

            // print the JSON response
            Console.WriteLine("ViewUrl:\n{0}", JsonConvert.SerializeObject(recipientView));
            Trace.WriteLine("ViewUrl:\n{0}", JsonConvert.SerializeObject(recipientView));

            // Start the embedded signing session
            System.Diagnostics.Process.Start(recipientView.Url);
