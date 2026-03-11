<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 520px;
            margin: 40px auto;
            background: #ffffff;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 24px rgba(0,0,0,0.10);
        }
        .header {
            background: linear-gradient(135deg, #ff6b35, #ffa500);
            padding: 32px 40px 24px;
            text-align: center;
        }
        .header h1 {
            color: #fff;
            font-size: 22px;
            margin: 0 0 6px;
            font-weight: 700;
        }
        .header p {
            color: rgba(255,255,255,0.85);
            font-size: 13px;
            margin: 0;
        }
        .body {
            padding: 36px 40px;
        }
        .greeting {
            font-size: 15px;
            color: #1a1a2e;
            font-weight: 600;
            margin-bottom: 16px;
        }
        .message-box {
            background: #fff8f5;
            border-left: 4px solid #ff6b35;
            border-radius: 8px;
            padding: 20px 24px;
            font-size: 15px;
            color: #333;
            line-height: 1.75;
            white-space: pre-line;
        }
        .footer {
            background: #f8f9fa;
            padding: 18px 40px;
            text-align: center;
            border-top: 1px solid #eee;
        }
        .footer p {
            font-size: 12px;
            color: #aaa;
            margin: 0;
            line-height: 1.6;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="header">
        <h1>📢 Yangi xabar</h1>
        <p>Ombor Boshqaruv Tizimi</p>
    </div>

    <div class="body">
        <div class="greeting">Assalomu alaykum, ${fullName}!</div>
        <div class="message-box">${messageText}</div>
    </div>

    <div class="footer">
        <p>
            Ushbu xabar ${senderName} tomonidan yuborilgan.<br>
            © ${.now?string("yyyy")} Ombor Boshqaruv Tizimi
        </p>
    </div>

</div>
</body>
</html>