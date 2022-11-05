# Spring Boot Thymeleaf

Spring Boot は、Java により、すばやく Web アプリケーションを作るためのフレームワーク。

しかし、Spring Boot には、画面を表示する機能がない。

そこで Spring Boot には、画面を表示するライブラリとして、Thymeleaf というテンプレートエンジンが用意されている。

ここでは、Spring Boot と Thymeleaf を使って、簡単なアプリを作ってみる。

## 環境

- Windows 11 Home
- Java 17 (Microsoft.OpenJDK.17)
- VSCode

拡張機能として、VSCode に以下をインストールしておく。

- Extension Pack for Java
- Spring Boot Extension Pack

## 手順

1. プロジェクトの作成
2. 


## 1. プロジェクトの作成

VSCode を開き、Ctrl + Shift + P キーを押す。

コマンドパレットが開いたら、`Spring Initializr` と入力して、
コマンド「Spring Initializr: Generate a Maven Project」を選択する。

次に `Specify Spring Boot version.` Spring Boot のバージョンを指定してね、と言われるので、使いたい Spring Boot のバージョンを指定する。

ここでは `2.6.14(SNAPSHOT)` を選択した。

次に、`Specify project language` と問われるので、プロジェクトに使用する言語を選択する。

ここでは、`Java` を選択。

次に `Input Group Id` グループ ID の入力を求められる。

ここでは、デフォルトのまま、「com.example」として、Enter キーを押下。

次に `Input Artifact Id` アーティファクト ID の入力を求められる。

アーティファクト ID と言われると何のことか分からないが、「プロジェクト名」的な役割として使われる ID となる。

ここでは、`io-test-app` （入出力テストアプリ）とした。

次に `Specify pacakaging type.` パッケージタイプの指定を求められる。

ここでは、「Jar」を選択した。

次に `Specify Java version.` と、使用する Java のバージョンを求められる。

ここでは、安定版の Java バージョンである 17 を指定した。

次に `Choose Dependencies.` 依存ライブラリを選択してね、と問われる

ここでは、以下の2つのライブラリを選択した。

- Spring Web
- Thymeleaf

この２つを選んで、「Select 2 Dependencies」となった状態で Enter キーを押下。

次に、プロジェクトを作成する場所を聞かれるので、
プロジェクトを作成するフォルダを選択する。

ファイルエクスプローラから、フォルダを選択したら、「Generate into this folder」ボタンを押下する。

Spring Boot プロジェクトの作成に成功すると `Successfully generated.` と
メッセージが表示される。

そのまま、プロジェクトを VSCode で開きたい場合は、「Open」ボタンをクリックする。

## プロジェクトの確認

プロジェクトを確認すると、プロジェクトにある Java クラスは、
`IoTestAppApplication.java` のみとなっている。

このクラスが、この Spring Boot アプリケーションの起点となる。

`src/main/java/com/example/iotestapp/IoTestAppApplication.java`
``` java
@SpringBootApplication
public class IoTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTestAppApplication.class, args);
	}
}
```

## 入力画面のコントローラを作成

まず始めに、入力画面表示の問い合わせ先のコントローラを作る。

コントローラ役のクラスには、`@Controller` アノテーションを付与する。

`@GetMapping` アノテーションで、問い合わせ先の URL を指定する。

Spring Boot の開発用サーバのトップアドレス URL は、`http://localhost:8080` なので、
以下のマッピングでは、入力画面表示の URL を `http://localhost:8080/input` を指定したことになる。

`src/main/java/com/example/iotestapp/controller/InputController.java`
``` java
@Controller
public class InputController {
    
    @GetMapping("/input")
    public String input() {
        return "input";
    }
}
```

また上記の例では、返り値を文字列「input」として指定していることで、
この後に説明する、`src/main/resource/template/input.html` ファイルを
表示する画面として指定したことになる。

## 入力画面の作成

次に入力画面を html ファイルで作成する。

Thymeleaf では、`src/main/resource/template` フォルダ配下に、
画面表示に使う html ファイルを配置する決まりになっている。

ここで、入力画面を作る時の大きなポイントは２つ。

１つは、form タグのアクションには、遷移先、出力画面の URL である `/output` を指定すること。

もう１つは、出力画面（のコントローラ）に送る、3つの input フォームの name 属性には、
送信する属性の固有名称をそれぞれ付けてあげることだ。

また、Thymeleaf 特有の属性とするものには、属性名に`th:`を付ける
（以下の例では、`th:action` や `th:method` が、それに当たる）。

form のメソッド属性には、`post` を指定する。

メソッド属性に指定するのは、サーバへデータを送信する形式で、
最もよく使われる送信形式には、GET と POST がある。

GET は、URL の中にデータを埋め込む形式だ。

それに対し、今回、メソッドに指定した、POST は、リクエストボディにデータを埋め込む形式。

サーバに送るデータには、ヘッダ情報とボディがあるが、POST メソッドでは、
そのボディにデータを埋め込んでサーバに問い合わせを行う。

`src/main/resource/template/input.html`
``` html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>入力画面</title>
    <link type="text/css" rel="stylesheet" href="./style.css">
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
        rel="stylesheet">
</head>

<body>
    <main class="container">
        <h1>入力画面</h1>
        <p>名前を入力してください</p>
        <form th:action="@{/output}" th:method="post">
            <div class="mb-3">
                <label for="accout-name" class="form-label">アカウント名</label>
                <input type="text" class="form-control" id="account-name" name="accountName">
            </div>
            <div class="mb-3">
                <label for="last-name" class="form-label">苗字</label>
                <input type="text" class="form-control" id="last-name" name="lastName">
            </div>
            <div class="mb-3">
                <label for="first-name" class="form-label">名前</label>
                <input type="text" class="form-control" id="first-name" name="firstName">
            </div>
            <button type="submit" class="btn btn-primary my-3">入力内容を送信する</button>
        </form>
    </main>
</body>

</html>
```

今回は、画面を簡単に整えるために、Bootstrap という CSS ライブラリを利用している。

読み込んでいる `https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css`
というライブラリがそれに当たる。

この読み込みをコメントアウトしたり、有効にしたりして、
サーバを再起動させて表示確認をすると、CSS ライブラリがやってくれていることが分かると思う。

Bootstrap は、ライブラリを読み込んで、
スタイリングしたいタグに、例えば `class="container"` や `class="mb-3"` など、
クラス属性を指定するだけで、簡単に表示をスタイリングさせることができる。

そして、今回は、自前のスタイルシートとして、`./style.css` を読み込むようにしている。

`.` （ドット）は相対パスと言われるもので、「ここ」を表す。

Spring Boot に配置するリソースファイルで、「ここ」を表す場所は、
`src/main/resources/static` となる。

なので、`src/main/resources/static` に `style.css` ファイルを作成する。

## style.css ファイルを作成する

今回作成するスタイルシートは、メインの表示領域を3文字分だけ下げ、
横幅の上限を 300px にする、簡単なスタイルシートだ。

`src/main/resources/static/style.css`
``` css
main {
    max-width: 300px !important;
    margin: 3rem 0;
}
``` 

## 一度、動かしてみる

ここまでできたら、アプリを一度動かしてみる。

アプリを動かすには、アプリの起点である `IoTestAppApplication.java` クラスを確認する。

`IoTestAppApplication.java` クラスを
VSCode のエディタで確認すると、main メソッドの上に「Run」ボタンが表示されているはずだ。

この「Run」ボタンをクリックすると、開発用サーバが起動し、
コンソールに起動ログが表示されるはずだ。

ログの出力が一旦止まれば、起動が完了しているので、
起動が完了したら、ブラウザで `http://localhost:8080/` を開く。

サーバが起動していれば、「Whitelabel Error Page ～」と表示される。

これは、無いページを開いたときに表示される画面。

今、作った「入力画面」は、コントローラで `http://localhost:8080/input` に
問い合わせが来た時の画面として作ったので、`http://localhost:8080/` は
まだ画面として用意されていないため、「Whitelabel Error Page ～」のような表示になる。

### 入力して送信

3つの入力フォームに内容を入力して、「送信」ボタンをクリックしてみる。

送信先を `input.html` にコーディングした form タグの `th:action` 属性で、
「http://localhost:8080/output」と指定したが、画面としては用意していないので、
当然表示は「Whitelabel Error Page ～」となる。

今から、この遷移先の「出力画面」を作っていく。

## 出力画面のコントローラを作成

出力画面表示の問い合わせ先として、コントローラを作成する。

フォームから送られてきたリクエストボディの情報をコントローラで受け取りたいので、
受け取るメソッドには、`PostMapping` アノテーションを付与する。

また `PostMapping` アノテーションには、問い合わせ先としたい URL を設定する。

以下の場合、「http://localhost:8080/output」を出力画面の
問い合わせ先として設定したことになる。

`src/main/java/com/example/iotestapp/controller/OutputController.java`
``` java
@Controller
public class OutputController {

    @PostMapping("/output")
    public String output(@RequestParam(required = false) String accountName,
            InputForm inputForm, Model model) {
        model.addAttribute("accountName", accountName);
        model.addAttribute("inputForm", inputForm);
        return "output";
    }
```

Spring Boot では、フォームから送られてきたデータを自動的に引数に紐づけてくれる。

紐づけられた値の受け取り方には、2つの方法がある。

１つが引数の変数名に一致させる方法。

そしてもう1つが、Setter を定義したクラスを引数にする方法だ。

引数の変数名に一致させる方法の場合、対象の引数に `RequestParam` アノテーションを付与する。

required 属性は、受け取りを必須にするかどうかの設定で、false の場合、受け取りは必須でなくなる。

今回、accountName 以外の2つの入力項目、lastName と firstName は、
「Setter を定義したクラス」で受け取るようにした。

では、これから、「Setter を定義したクラス」として、InputForm クラスを作っていく。

## InputForm クラスの作成

フォームの入力項目に沿った、フィールドとそれに対する Setter と Getter を持った、
ごく単純なクラスを作る。

`src/main/java/com/example/iotestapp/form/InputForm.java`
``` java
public class InputForm {
    private String lastName;
    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
```

## 出力画面の作成

最後に出力画面を html ファイルで作成する。

model に設定した変数を出力したいタグの属性に、
`th:text="${accountName}` を設定する。

`src/main/resource/template/output.html`
``` html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="./style.css">
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
        rel="stylesheet">
    <title>出力画面</title>
</head>

<body>
    <main class="container">
        <h1>出力画面</h1>
        <p>フォームに入力された内容を表示します</p>
        <table class="table table-success table-striped">
            <thead class="table-light">
                <tr>
                    <th>項目</th>
                    <th>入力値</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th>アカウント名</th>
                    <td th:text="${accountName}">accountName</td>
                </tr>
                <tr>
                    <th>苗字</th>
                    <td th:text="${inputForm.lastName}">lastName</td>
                </tr>
                <tr>
                    <th>名前</th>
                    <td th:text="${inputForm.firstName}">firstName</td>
                </tr>
            </tbody>
        </table>
        <a class="btn btn-primary my-3" href="./input">入力画面に戻る</a>
    </main>
</body>

</html>
```

## 最後に動作確認

ひと通り必要なファイルを作成したので、最後に動かしてみる。

これで、入力・出力の動作確認ができるはずだ。

いろいろ、コードの内容を変えて、動きを確かめよう。

以上。