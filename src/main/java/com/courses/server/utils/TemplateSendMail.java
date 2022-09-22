package com.courses.server.utils;

public class TemplateSendMail {
    public static String getContent(String link, String title, String description) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <style type=\"text/css\">\n" +
                "      @media screen {\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: normal;\n" +
                "          font-weight: 400;\n" +
                "          src: local(\"Lato Regular\"), local(\"Lato-Regular\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff)\n" +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: normal;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold\"), local(\"Lato-Bold\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff)\n" +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 400;\n" +
                "          src: local(\"Lato Italic\"), local(\"Lato-Italic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff)\n" +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold Italic\"), local(\"Lato-BoldItalic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff)\n" +
                "              format(\"woff\");\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* CLIENT-SPECIFIC STYLES */\n" +
                "      body,\n" +
                "      table,\n" +
                "      td,\n" +
                "      a {\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table,\n" +
                "      td {\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "\n" +
                "      /* RESET STYLES */\n" +
                "      img {\n" +
                "        border: 0;\n" +
                "        height: auto;\n" +
                "        line-height: 100%;\n" +
                "        outline: none;\n" +
                "        text-decoration: none;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-collapse: collapse !important;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        height: 100% !important;\n" +
                "        margin: 0 !important;\n" +
                "        padding: 0 !important;\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      /* iOS BLUE LINKS */\n" +
                "      a[x-apple-data-detectors] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: none !important;\n" +
                "        font-size: inherit !important;\n" +
                "        font-family: inherit !important;\n" +
                "        font-weight: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      /* MOBILE STYLES */\n" +
                "      @media screen and (max-width: 600px) {\n" +
                "        h1 {\n" +
                "          font-size: 32px !important;\n" +
                "          line-height: 32px !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* ANDROID CENTER FIX */\n" +
                "      div[style*=\"margin: 16px 0;\"] {\n" +
                "        margin: 0 !important;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      background-color: #f4f4f4;\n" +
                "      margin: 0 !important;\n" +
                "      padding: 0 !important;\n" +
                "    \"\n" +
                "  >\n" +
                "    <!-- HIDDEN PREHEADER TEXT -->\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        display: none;\n" +
                "        font-size: 1px;\n" +
                "        color: #fefefe;\n" +
                "        line-height: 1px;\n" +
                "        font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "        max-height: 0px;\n" +
                "        max-width: 0px;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "      We're thrilled to have you here! Get ready to dive into your new account.\n" +
                "    </div>\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "      <!-- LOGO -->\n" +
                "      <tr>\n" +
                "        <td bgcolor=\"#FFA73B\" align=\"center\">\n" +
                "          <table\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"max-width: 600px\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                align=\"center\"\n" +
                "                valign=\"top\"\n" +
                "                style=\"padding: 40px 10px 40px 10px\"\n" +
                "              ></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px\">\n" +
                "          <table\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"max-width: 600px\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"center\"\n" +
                "                valign=\"top\"\n" +
                "                style=\"\n" +
                "                  padding: 40px 20px 20px 20px;\n" +
                "                  border-radius: 4px 4px 0px 0px;\n" +
                "                  color: #111111;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 48px;\n" +
                "                  font-weight: 400;\n" +
                "                  letter-spacing: 4px;\n" +
                "                  line-height: 48px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <h1 style=\"font-size: 48px; font-weight: 400; margin: 2\">\n" +
                "                  Welcome!\n" +
                "                </h1>\n" +
                "                <img\n" +
                "                  src=\" https://img.icons8.com/clouds/100/000000/handshake.png\"\n" +
                "                  width=\"125\"\n" +
                "                  height=\"120\"\n" +
                "                  style=\"display: block; border: 0px\"\n" +
                "                />\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px\">\n" +
                "          <table\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"max-width: 600px\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 20px 30px 40px 30px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  Welcome to the GenzLiin Store. " + description + " Just hit the button below.\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                  <tr>\n" +
                "                    <td\n" +
                "                      bgcolor=\"#ffffff\"\n" +
                "                      align=\"center\"\n" +
                "                      style=\"padding: 20px 30px 60px 30px\"\n" +
                "                    >\n" +
                "                      <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            align=\"center\"\n" +
                "                            style=\"border-radius: 3px\"\n" +
                "                            bgcolor=\"#FFA73B\"\n" +
                "                          >\n" +
                "                            <a\n" +
                "                              href=\""+ link +"\"\n" +
                "                              target=\"_blank\"\n" +
                "                              style=\"\n" +
                "                                font-size: 20px;\n" +
                "                                font-family: Helvetica, Arial, sans-serif;\n" +
                "                                color: #ffffff;\n" +
                "                                text-decoration: none;\n" +
                "                                color: #ffffff;\n" +
                "                                text-decoration: none;\n" +
                "                                padding: 15px 25px;\n" +
                "                                border-radius: 2px;\n" +
                "                                border: 1px solid #ffa73b;\n" +
                "                                display: inline-block;\n" +
                "                              \"\n" +
                "                              > " + title + " </a\n" +
                "                            >\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <!-- COPY -->\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 0px 30px 0px 30px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  If that doesn't work, copy and paste the following link in\n" +
                "                  your browser:\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <!-- COPY -->\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 20px 30px 20px 30px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  <a href=\"" + link + "\" target=\"_blank\" style=\"color: #ffa73b\"\n" +
                "                    >" + link + "</a\n" +
                "                  >\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 0px 30px 20px 30px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  If you have any questions, just reply to this\n" +
                "                  email&mdash;we're always happy to help out.\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#ffffff\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 0px 30px 40px 30px;\n" +
                "                  border-radius: 0px 0px 4px 4px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <p style=\"margin: 0\">Cheers,<br />GenzLiin Store</p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td\n" +
                "          bgcolor=\"#f4f4f4\"\n" +
                "          align=\"center\"\n" +
                "          style=\"padding: 30px 10px 0px 10px\"\n" +
                "        >\n" +
                "          <table\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"max-width: 600px\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#FFECD1\"\n" +
                "                align=\"center\"\n" +
                "                style=\"\n" +
                "                  padding: 30px 30px 30px 30px;\n" +
                "                  border-radius: 4px 4px 4px 4px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 18px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 25px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <h2\n" +
                "                  style=\"\n" +
                "                    font-size: 20px;\n" +
                "                    font-weight: 400;\n" +
                "                    color: #111111;\n" +
                "                    margin: 0;\n" +
                "                  \"\n" +
                "                >\n" +
                "                  Need more help?\n" +
                "                </h2>\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  <a\n" +
                "                    href=\"https://www.facebook.com/genzliin.store\"\n" +
                "                    target=\"_blank\"\n" +
                "                    style=\"color: #ffa73b\"\n" +
                "                    >We&rsquo;re here to help you out</a\n" +
                "                  >\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px\">\n" +
                "          <table\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"max-width: 600px\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                bgcolor=\"#f4f4f4\"\n" +
                "                align=\"left\"\n" +
                "                style=\"\n" +
                "                  padding: 0px 30px 30px 30px;\n" +
                "                  color: #666666;\n" +
                "                  font-family: 'Lato', Helvetica, Arial, sans-serif;\n" +
                "                  font-size: 14px;\n" +
                "                  font-weight: 400;\n" +
                "                  line-height: 18px;\n" +
                "                \"\n" +
                "              >\n" +
                "                <br />\n" +
                "                <p style=\"margin: 0\">\n" +
                "                  If these emails get annoying, please feel free to\n" +
                "                  <a\n" +
                "                    href=\"#\"\n" +
                "                    target=\"_blank\"\n" +
                "                    style=\"color: #111111; font-weight: 700\"\n" +
                "                    >unsubscribe</a\n" +
                "                  >.\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}

