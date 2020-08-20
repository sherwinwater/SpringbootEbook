package com.sherwin.ebook.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String YOUR_DOMAIN_NAME = System.getenv("MAILGUN_DOMAIN"); //TODO put your domain name here
    public static String API_KEY = System.getenv("MAILGUN_API_KEY"); //TODO put your Private API Key here

    public JsonNode sendSimpleMessage() throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/"
                + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .field("from", "Sherwin W sherwin.water@gmail.com")
                .field("to", "sherwin.water2@gmail.com")//TODO put your emails here
                .field("subject", "Hello From Students")
//                .field("text", "Testing Students")
//                .field("html","<html><a href='http://stackoverflow.com'>HTML version of the body</a></html>")
//                .field("html",html)
                .field("html",billinghtml)
                .asJson();
        return request.getBody();
    }

    public static JsonNode createTemplate() throws UnirestException {

        HttpResponse <JsonNode> request = Unirest.post("https://api.mailgun.net/v3/"
                + YOUR_DOMAIN_NAME + "/templates")
                .basicAuth("api", API_KEY)
                .field("name", "template.name")
                .field("description", "template description")
                .asJson();

        return request.getBody();
    }

    String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "<meta name=\"viewport\" content=\"width=device-width\" />\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "<title>Alerts e.g. approaching your limit</title>\n" +
            "<link href=\"styles.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />\n" +
            "</head>\n" +
            "\n" +
            "<body itemscope itemtype=\"http://schema.org/EmailMessage\">\n" +
            "\n" +
            "<table class=\"body-wrap\">\n" +
            "\t<tr>\n" +
            "\t\t<td></td>\n" +
            "\t\t<td class=\"container\" width=\"600\">\n" +
            "\t\t\t<div class=\"content\">\n" +
            "\t\t\t\t<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td class=\"alert alert-warning\">\n" +
            "\t\t\t\t\t\t\tWarning: You're approaching your limit. Please upgrade.\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td class=\"content-wrap\">\n" +
            "\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
            "\t\t\t\t\t\t\t\t\t\tYou have <strong>1 free report</strong> remaining.\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
            "\t\t\t\t\t\t\t\t\t\tAdd your credit card now to upgrade your account to a premium plan to ensure you don't miss out on any reports.\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a href=\"http://www.mailgun.com\" class=\"btn-primary\">Upgrade my account</a>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
            "\t\t\t\t\t\t\t\t\t\tThanks for choosing Acme Inc.\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t<div class=\"footer\">\n" +
            "\t\t\t\t\t<table width=\"100%\">\n" +
            "\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t<td class=\"aligncenter content-block\"><a href=\"http://www.mailgun.com\">Unsubscribe</a> from these alerts.</td>\n" +
            "\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t</table>\n" +
            "\t\t\t\t</div></div>\n" +
            "\t\t</td>\n" +
            "\t\t<td></td>\n" +
            "\t</tr>\n" +
            "</table>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

String billinghtml ="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "<head>\n" +
        "<meta name=\"viewport\" content=\"width=device-width\" />\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "<title>Billing e.g. invoices and receipts</title>\n" +
        "<link href=\"styles.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />\n" +
        "</head>\n" +
        "\n" +
        "<body itemscope itemtype=\"http://schema.org/EmailMessage\">\n" +
        "\n" +
        "<table class=\"body-wrap\">\n" +
        "\t<tr>\n" +
        "\t\t<td></td>\n" +
        "\t\t<td class=\"container\" width=\"600\">\n" +
        "\t\t\t<div class=\"content\">\n" +
        "\t\t\t\t<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t<td class=\"content-wrap aligncenter\">\n" +
        "\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
        "\t\t\t\t\t\t\t\t\t\t<h1 class=\"aligncenter\">$33.98 Paid</h1>\n" +
        "\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t<td class=\"content-block\">\n" +
        "\t\t\t\t\t\t\t\t\t\t<h2 class=\"aligncenter\">Thanks for using Acme Inc.</h2>\n" +
        "\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t<td class=\"content-block aligncenter\">\n" +
        "\t\t\t\t\t\t\t\t\t\t<table class=\"invoice\">\n" +
        "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t<td>Lee Munroe<br>Invoice #12345<br>June 01 2014</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"invoice-items\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>Service 1</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"alignright\">$ 19.99</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>Service 2</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"alignright\">$ 9.99</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>Service 3</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"alignright\">$ 4.00</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class=\"total\">\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"alignright\" width=\"80%\">Total</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"alignright\">$ 33.98</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t\t\t</table>\n" +
        "\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t<td class=\"content-block aligncenter\">\n" +
        "\t\t\t\t\t\t\t\t\t\t<a href=\"http://www.mailgun.com\">View in browser</a>\n" +
        "\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t\t\t<td class=\"content-block aligncenter\">\n" +
        "\t\t\t\t\t\t\t\t\t\tAcme Inc. 123 Van Ness, San Francisco 94102\n" +
        "\t\t\t\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t\t\t</table>\n" +
        "\t\t\t\t\t\t</td>\n" +
        "\t\t\t\t\t</tr>\n" +
        "\t\t\t\t</table>\n" +
        "\t\t\t\t<div class=\"footer\">\n" +
        "\t\t\t\t\t<table width=\"100%\">\n" +
        "\t\t\t\t\t\t<tr>\n" +
        "\t\t\t\t\t\t\t<td class=\"aligncenter content-block\">Questions? Email <a href=\"mailto:\">support@acme.inc</a></td>\n" +
        "\t\t\t\t\t\t</tr>\n" +
        "\t\t\t\t\t</table>\n" +
        "\t\t\t\t</div></div>\n" +
        "\t\t</td>\n" +
        "\t\t<td></td>\n" +
        "\t</tr>\n" +
        "</table>\n" +
        "\n" +
        "</body>\n" +
        "</html>";
}
