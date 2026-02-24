<#ftl encoding='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="uz">

<head>
    <title>Xush kelibsiz!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
    <style type="text/css">
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 16px;
            margin: 0;
            padding: 0;
        }
    </style>
</head>

<body style="margin: 0; padding: 0; background-color: #f4f4f4;">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="600"
       style="border-collapse: collapse; margin-top: 40px;">

    <!-- HEADER -->
    <tr>
        <td bgcolor="#6c63ff" style="padding: 40px 30px; text-align: center;">
            <h1 style="color: #ffffff; margin: 0; font-size: 26px; font-family: 'Roboto', sans-serif;">
                Xush kelibsiz!
            </h1>
            <p style="color: rgba(255,255,255,0.85); margin: 8px 0 0 0; font-size: 15px;">
                Tizimga muvaffaqiyatli ro'yxatdan o'tdingiz
            </p>
        </td>
    </tr>

    <!-- BODY -->
    <tr>
        <td bgcolor="#ffffff" style="padding: 40px 30px;">
            <p style="font-size: 17px; color: #2d3748; margin: 0 0 16px 0;">
                Assalomu alaykum, <b>${fullName}</b>!
            </p>
            <p style="font-size: 15px; color: #4a5568; line-height: 1.7; margin: 0 0 30px 0;">
                Bizning tizimimizga xush kelibsiz! Quyida sizning tizimga kirish
                ma'lumotlaringiz keltirilgan. Iltimos, ushbu ma'lumotlarni maxfiy saqlang.
            </p>

            <!-- CREDENTIALS TABLE -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                   style="border-collapse: collapse; background-color: #f7f8fc; border: 1px solid #e2e8f0;">
                <tr>
                    <td style="padding: 12px 20px; border-bottom: 1px solid #e2e8f0;">
                        <span style="font-size: 14px; color: #718096;">Username</span>
                    </td>
                    <td style="padding: 12px 20px; border-bottom: 1px solid #e2e8f0; text-align: right;">
                        <b style="background-color: lightyellow; padding: 4px 12px;
                                  font-family: monospace; font-size: 15px; color: #2d3748;">
                            ${username}
                        </b>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 12px 20px;">
                        <span style="font-size: 14px; color: #718096;">Parol</span>
                    </td>
                    <td style="padding: 12px 20px; text-align: right;">
                        <b style="background-color: lightyellow; padding: 4px 12px;
                                  font-family: monospace; font-size: 15px; color: #2d3748;">
                            ${password}
                        </b>
                    </td>
                </tr>
            </table>

        </td>
    </tr>

    <!-- TELEGRAM BOT -->
    <tr>
        <td bgcolor="#e6fffa" style="padding: 24px 30px; border-top: 1px solid #b2f5ea;">
            <p style="margin: 0; font-size: 15px; color: #234e52;">
                Tizimdan foydalanish uchun Telegram botimizga murojaat qiling:
                <a href="https://t.me/masalan_bot"
                   style="color: #319795; font-weight: bold; text-decoration: none;">
                    @masalan_bot
                </a>
            </p>
        </td>
    </tr>

    <!-- FOOTER -->
    <tr>
        <td bgcolor="#eaeaea" style="padding: 24px 30px; text-align: center;">
            <p style="margin: 0; font-size: 13px; color: #888888; line-height: 1.6;">
                Bu xabar avtomatik yuborilgan. Iltimos, javob qaytarmang.<br/>
                Savollar bo'lsa, qo'llab-quvvatlash xizmatiga murojaat qiling.
            </p>
        </td>
    </tr>

</table>

</body>
</html>
