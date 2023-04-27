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
                "            url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: normal;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold\"), local(\"Lato-Bold\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 400;\n" +
                "          src: local(\"Lato Italic\"), local(\"Lato-Italic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold Italic\"), local(\"Lato-BoldItalic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff)\n"
                +
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
                "      Chúng tôi rất vui mừng khi có bạn ở đây! Hãy sẵn sàng để đi sâu vào tài khoản mới của bạn.\n" +
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
                "                  Chào mừng bạn đến với LRS education. " + description + " Bạn chỉ cần nhấn vào nút bên dưới.\n" +
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
                "                              href=\"" + link + "\"\n" +
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
                "                  Nếu không hoạt động bạn vui lòng truy cập vào đường linh sau:\n" +
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
                "                  Nếu bạn có bất cứ câu hỏi nào, vui lòng trả lời qua mail này\n" +
                "                  &mdash;Chúng tôi rất vui nếu nhận được câu hỏi từ bạn.\n" +
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
                "                <p style=\"margin: 0\">Cảm ơn,<br />LRS education</p>\n" +
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
                "                    href=\"https://www.facebook.com/nguyenvanhung13\"\n" +
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

    public static String getContentCourse(String link, String title, String description, String category, String action) {
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
                "            url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: normal;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold\"), local(\"Lato-Bold\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 400;\n" +
                "          src: local(\"Lato Italic\"), local(\"Lato-Italic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff)\n"
                +
                "              format(\"woff\");\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "          font-family: \"Lato\";\n" +
                "          font-style: italic;\n" +
                "          font-weight: 700;\n" +
                "          src: local(\"Lato Bold Italic\"), local(\"Lato-BoldItalic\"),\n" +
                "            url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff)\n"
                +
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
                title + ".\n" +
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
                "                  Chào mừng bạn đến với LRS education. " + category + "\n" + " của bạn đã được " + action + " thành công" +
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
                "                              href=\"" + link + "\"\n" +
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
                "                              > Đăng nhập </a\n" +
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
                "                <p style=\"margin: 0\">\n" + description + "\n" +
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
                "                   Nếu bạn có bất kì câu hỏi nào, Vui lòng trả lời qua mail này\n" +
                "                  &mdash;Chúng tôi rất vui khi nhân được câu hỏi từ bạn.\n" +
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
                "                <p style=\"margin: 0\">Cảm ơn,<br />LRS education</p>\n" +
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
                "                    href=\"https://www.facebook.com/cong.nguyenngoc.50159\"\n" +
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

    public static String getSuccess(String link) {
        return "<div\n" +
                "            style=\"\n" +
                "                width: 100vw;\n" +
                "                height: 100vh;\n" +
                "                background-color: #f6f6f6;\n" +
                "                display: flex;\n" +
                "                justify-content: center;\n" +
                "                font-family: 'Manrope', sans-serif;\n" +
                "            \"\n" +
                "        >\n" +
                "            <div\n" +
                "                style=\"\n" +
                "                    width: 350px;\n" +
                "                    padding: 40px 20px;\n" +
                "                    text-align: center;\n" +
                "                    margin-top: 100px;\n" +
                "                    height: fit-content;\n" +
                "                    border-radius: 16px;\n" +
                "                    border: 3px solid #2bc63a;\n" +
                "                    box-shadow: 4px 5px 50px rgb(85 85 85 / 50%);\n" +
                "                \"\n" +
                "            >\n" +
                "                <img\n" +
                "                    src=\"https://www.seekpng.com/png/detail/72-722839_success-save-success-png-icon.png\"\n"
                +
                "                    alt=\"\"\n" +
                "                    style=\"width: 100px\"\n" +
                "                />\n" +
                "                <h2 style=\"font-size: 36px; margin: 10px\">Congratulation</h2>\n" +
                "                <p style=\"line-height: 1.7\">\n" +
                "                    Your account has been successfully created. Click button\n" +
                "                    bellow to login\n" +
                "                </p>\n" +
                "                <a\n" +
                "                    href=\"" + link + "\"\n" +
                "                    style=\"\n" +
                "                        color: #fff;\n" +
                "                        padding: 12px 20px;\n" +
                "                        font-size: 15px;\n" +
                "                        cursor: pointer;\n" +
                "                        border-width: 0;\n" +
                "                        line-height: 1.42857;\n" +
                "                        border-radius: 3px;\n" +
                "                        transition: all 0.5s;\n" +
                "                        background-color: #efbb20;\n" +
                "                        text-decoration: none;\n" +
                "                    \"\n" +
                "                    onmouseover=\"this.style.backgroundColor='#4c1864'\"\n" +
                "                    onmouseleave=\"this.style.backgroundColor='#efbb20'\"\n" +
                "                >\n" +
                "                    Login\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div>";
    }

    public static String getError(String content, String link, String tilteLink) {
        return "<div\n" +
                "\t\t\tstyle=\"\n" +
                "\t\t\t\twidth: 100vw;\n" +
                "\t\t\t\theight: 100vh;\n" +
                "\t\t\t\tbackground-color: #f6f6f6;\n" +
                "\t\t\t\tdisplay: flex;\n" +
                "\t\t\t\tjustify-content: center;\n" +
                "\t\t\t\tfont-family: 'Manrope', sans-serif;\n" +
                "\t\t\t\"\n" +
                "\t\t>\n" +
                "\t\t\t<div\n" +
                "\t\t\t\tstyle=\"\n" +
                "\t\t\t\t\twidth: 350px;\n" +
                "\t\t\t\t\tpadding: 40px 20px;\n" +
                "\t\t\t\t\ttext-align: center;\n" +
                "\t\t\t\t\tmargin-top: 100px;\n" +
                "\t\t\t\t\theight: fit-content;\n" +
                "\t\t\t\t\tborder-radius: 16px;\n" +
                "\t\t\t\t\tborder: 3px solid #c62b2b;\n" +
                "\t\t\t\t\tbox-shadow: 4px 5px 50px rgb(85 85 85 / 50%);\n" +
                "\t\t\t\t\"\n" +
                "\t\t\t>\n" +
                "\t\t\t\t<img\n" +
                "\t\t\t\t\tsrc=\"https://www.seekpng.com/png/detail/207-2074918_error-cross-icon-symbol-error-error-error-error.png\"\n"
                +
                "\t\t\t\t\talt=\"\"\n" +
                "\t\t\t\t\tstyle=\"width: 100px\"\n" +
                "\t\t\t\t/>\n" +
                "\t\t\t\t<h2 style=\"font-size: 36px; margin: 10px\">Error</h2>\n" +
                "\t\t\t\t<p style=\"line-height: 1.7\">\n" +
                "\t\t\t\t\t" + content + "\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<a\n" +
                "\t\t\t\t\thref=\" " + link + "\"\n" +
                "\t\t\t\t\tstyle=\"\n" +
                "\t\t\t\t\t\tcolor: #fff;\n" +
                "\t\t\t\t\t\tpadding: 12px 20px;\n" +
                "\t\t\t\t\t\tfont-size: 15px;\n" +
                "\t\t\t\t\t\tcursor: pointer;\n" +
                "\t\t\t\t\t\tborder-width: 0;\n" +
                "\t\t\t\t\t\tline-height: 1.42857;\n" +
                "\t\t\t\t\t\tborder-radius: 3px;\n" +
                "\t\t\t\t\t\ttransition: all 0.5s;\n" +
                "\t\t\t\t\t\tbackground-color: #efbb20;\n" +
                "\t\t\t\t\t\ttext-decoration: none;\n" +
                "\t\t\t\t\t\"\n" +
                "\t\t\t\t\tonmouseover=\"this.style.backgroundColor='#4c1864'\"\n" +
                "\t\t\t\t\tonmouseleave=\"this.style.backgroundColor='#efbb20'\"\n" +
                "\t\t\t\t>\n" +
                "\t\t\t\t\t" + tilteLink + "\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>";
    }
}
