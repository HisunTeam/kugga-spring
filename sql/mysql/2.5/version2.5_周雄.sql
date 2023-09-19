INSERT INTO `email_template` (`email_scene`, `email_type`, `locale`, `subject`, `template`, `send_interval`,
                              `send_limit`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('FORGET_PASSWORD', 'SIMPLE_HTML_MAIL', 'en-US', 'Forget password',
        '<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n    <title></title>\n    <style>\nimg{border:none;-ms-interpolation-mode:bicubic;max-width:100%}\nbody{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}\ntable{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}\ntable td{font-family:sans-serif;font-size:14px;vertical-align:top}\n.body{background-color:#f2f3f4;width:100%}\n.container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}\n.content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}\n.main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}\n.wrapper{box-sizing:border-box;padding:40px}\n.content-block{padding-bottom:10px;padding-top:10px}\n.btn{box-sizing:border-box;width:100%}\n.btn > tbody > tr > td{padding-bottom:15px}\n.btn table{width:auto}\n.btn table td{background-color:#ffffff;border-radius:40px;text-align:center}\n.btn a{background-color:#ffffff;border:solid 1px #182034;border-radius:40px;box-sizing:border-box;color:#182034;cursor:pointer;display:inline-block;font-size:24px;font-weight:700;margin:0;padding:18px 85px;text-decoration:none;text-transform:capitalize}\n.btn-primary table td{background-color:#182034}\n.btn-primary a{background-color:#182034;border-color:#182034;color:#ffffff}\nh1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}\nh1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}\np,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}\np li,ul li,ol li{list-style-position:inside;margin-left:5px}\na{color:#EB4141;text-decoration:underline}\n    </style>\n  </head>\n  <body>\n    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n      <tr>\n        <td>&nbsp;</td>\n        <td class=\"container\">\n          <div class=\"content\">\n            <table role=\"presentation\" class=\"main\">\n              <tr>\n                <td class=\"wrapper\">\n                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                      <td>\n                        <p style=\"text-align: center\">Hi, We received a request to reset the password on your Duke Account.</p>\n                      </td>\n                    </tr>\n                    <tr><td>&nbsp;</td></tr>\n                    <tr>\n                      <td>\n                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n                          <tbody>\n                            <tr>\n                              <td align=\"center\">\n                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                  <tbody>\n                                    <tr>\n                                      <td><a href=\"{}\" target=\"_blank\">Click to reset</a> </td>\n                                    </tr>\n                                  </tbody>\n                                </table>\n                              </td>\n                            </tr>\n                          </tbody>\n                        </table>\n                      </td>\n                    </tr>\n					<tr>\n						<td>\n						 <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"number\">\n                          <tbody>\n                            <tr>\n                              <td align=\"center\">\n                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                  <tbody>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Please don\'t share this link with anyone else in order to protect the security of your account. It is only valid for 30 minutes. Thanks for helping us keep your account secure.</p>\n                                      </td>\n                                    </tr>\n                                  </tbody>\n                                </table>\n                              </td>\n                            </tr>\n                          </tbody>\n                        </table>\n						</td>\n					</tr>\n                    <tr><td>&nbsp;</td></tr>\n                    <tr>\n                        <td>\n                            <p>Best wishes,<br/>The KuggaDuke</p>\n                        </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n            </table>\n          </div>\n        </td>\n        <td>&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>',
        60, 100, NULL, NULL, NULL, NULL, b'0');

update email_template
set template = '<!doctype html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
    <style>
img{border:none;-ms-interpolation-mode:bicubic;max-width:100%}
body{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}
table{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}
table td{font-family:sans-serif;font-size:14px;vertical-align:top}
.body{background-color:#f2f3f4;width:100%}
.container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}
.content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}
.main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}
.wrapper{box-sizing:border-box;padding:40px}
.content-block{padding-bottom:10px;padding-top:10px}
.duke_btn{box-sizing:border-box;width:100%}
.duke_btn > tbody > tr > td{padding-bottom:15px}
.duke_btn table{width:auto}
.duke_btn table td{background-color:#ffffff;border-radius:40px;text-align:center}
.duke_btn a{background-color:#ffffff;border:solid 1px #182034;border-radius:40px;box-sizing:border-box;color:#182034;cursor:pointer;display:inline-block;font-size:24px;font-weight:700;margin:0;padding:18px 85px;text-decoration:none;text-transform:capitalize}
.duke_btn-primary table td{background-color:#182034}
.duke_btn-primary a{background-color:#182034;border-color:#182034;color:#ffffff;text-decoration: none !important;}
h1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}
h1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}
p,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}
p li,ul li,ol li{list-style-position:inside;margin-left:5px}
a{color:#EB4141;text-decoration:underline}
    </style>
  </head>
  <body>
    <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="body">
      <tr>
        <td>&nbsp;</td>
        <td class="container">
          <div class="content">
            <table role="presentation" class="main">
              <tr>
                <td class="wrapper">
                  <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <p style="text-align: center">Hi, We received a request to reset the password on your Duke Account.</p>
                      </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                      <td>
                        <table border="0" cellpadding="0" cellspacing="0" class="duke_btn duke_btn-primary">
                          <tbody>
                            <tr>
                              <td align="center">
                                <table border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                    <tr>
                                      <td><a href="{}" target="_blank">Click to Reset</a> </td>
                                    </tr>
                                  </tbody>
                                </table>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <p style="font-size: 16px;color: #999;text-align: center;">Please don''t share this link with anyone else in order to protect the security of your account. It is only valid for 30 minutes. Thanks for helping us keep your account secure.</p>
                      </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td>
                            <p>Best wishes,<br/>The KuggaDuke</p>
                        </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </div>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </body>
</html>'
where email_scene = 'FORGET_PASSWORD'
  and locale = 'en-US';
