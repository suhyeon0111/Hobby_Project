# Hoppy JavaScript Coding Convention

Hoppy 자바스크립트 코딩 컨벤션 가이드는
[네이버 자바스크립트 스타일 가이드](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md)
를 기준으로 참조하여 작성되었습니다.

##### - Contents Standard
- Required : 필수 사항 (Must Do)
- Recommendations : 권장 사항 (Have To Do)

## Table Of Contents

  1. [Referances](#referances)
  2. [Arrays](#arrays)
  3. [Strings](#strings)
  4. [Functions](#functions)
  5. [Arrow Functions](#arrow-functions)
  6. [Classes & Constructors](#classes--constructors)
  7. [Modules](#modules)
  8. [Properties](#properties)
  9. [Variables](#variables)
  10. [Comparison Operators & Equality](#comparison-operators--equality)
  11. [Blocks](#blocks)
  12. [Comments](#comments)
  13. [Whitespace](#whitespace)
  14. [Commas](#commas)
  15. [Naming Conventions](#naming-conventions)
  16. [jQuery](#jquery)


## Referances

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [1.1](#1.1) <a name='1.1'></a> 변수 선언은 가급적 `const` 를 사용하고, `var` 를 사용하지 않는다. eslint: [`prefer-const`](http://eslint.org/docs/rules/prefer-const.html), [`no-const-assign`](http://eslint.org/docs/rules/no-const-assign.html)

    ```javascript
    // bad
    var a = 1;
    var b = 2;

    // good
    const a = 1;
    const b = 2;
    ```

##### - Recommendations

- [1.2](#1.2) <a name='1.2'></a> 참조를 재할당 해야한다면 `var` 대신 `let` 을 사용한다.  eslint: [`no-var`](http://eslint.org/docs/rules/no-var.html) jscs: [`disallowVar`](http://jscs.info/rule/disallowVar)

    ```javascript
    // bad
    var count = 1;
    if (true) {
      count += 1;
    }

    // good, use the let.
    let count = 1;
    if (true) {
      count += 1;
    }
    ```

- [1.3](#1.3) <a name='1.3'></a> `let` 과 `const` 는 선언된 블록 안에서만 존재하는 블록 스코프이다.

    ```javascript
    // const 와 let 은 선언된 블록의 안에서만 존재한다.
    {
      let a = 1;
      const b = 1;
    }
    console.log(a); // ReferenceError
    console.log(b); // ReferenceError
    ```**
    
**[⬆ back to top](#table-of-contents)**

## Arrays

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Recommendations

- [2.1](#2.1) <a name='2.1'></a>배열이 멀티 라인인 경우, 배열의 시작(before)과 끝(after)은 개행한다.

    ```javascript
    // bad
    const arr = [
      [0, 1], [2, 3], [4, 5],
    ];

    const objectInArray = [{
      id: 1,
    }, {
      id: 2,
    }];

    const numberInArray = [
      1, 2,
    ];

    // good
    const arr = [[0, 1], [2, 3], [4, 5]];

    const objectInArray = [
      {
        id: 1,
      },
      {
        id: 2,
      },
    ];

    const numberInArray = [
      1,
      2,
    ];
    ```

**[⬆ back to top](#table-of-contents)**

## Strings

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [3.1](#3.1) <a name='3.1'></a>따옴표는 쌍따옴표를 사용한다. 이스케이프한 경우는 예외로 홑따옴표를 사용할 수 있다. eslint: [`quotes`](http://eslint.org/docs/rules/quotes.html)

    ```javascript
    // bad
    var key = 'naver';
    var obj = {
      'key': '1'
    }

    // good
    var key = "naver";
    var obj = {
      "key": "1"
    }
    ```
    
##### - Recommendations
    
- [3.2](#3.2) <a name='3.2'></a> 프로그램에서 문자열을 생성하는 경우는 문자열 연결이 아닌 template strings를 이용한다. eslint: [`prefer-template`](http://eslint.org/docs/rules/prefer-template.html) [`template-curly-spacing`](http://eslint.org/docs/rules/template-curly-spacing)

    ```javascript
    // bad
    function sayHi(name) {
      return "How are you, " + name + "?";
    }

    // bad
    function sayHi(name) {
      return ["How are you, ", name, "?"].join();
    }

    // good
    function sayHi(name) {
      return `How are you, ${name}?`;
    }
    ```
    
**[⬆ back to top](#table-of-contents)**

## Functions

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required
    
- [4.1](#4.1) <a name='4.1'></a>함수의 정의가 멀티라인 인 경우, 오브젝트와 같은 스타일 가이드를 따른다.

    ```javascript
    // bad
    function foo(bar,
                 baz,
                 quux) {
      // ...
    }

    // good
    function foo(
      bar,
      baz,
      quux,
    ) {
      // ...
    }

    // bad
    console.log(foo,
      bar,
      baz);

    // good
    console.log(
      foo,
      bar,
      baz,
    );
    ```    

- [4.2](#4.2) <a name='4.2'></a> 절대로 파라미터에 `arguments` 를 지정하지 않는다. 이것은 함수 스코프에 전해지는  `arguments` 오브젝트의 참조를 덮어 버린다.

    ```javascript
    // bad
    function nope(name, options, arguments) {
      // ...stuff...
    }

    // good
    function yup(name, options, args) {
      // ...stuff...
    }
    ```

- [4.3](#4.3) <a name='4.3'></a> 함수 이외의 블록 (if나 while같은) 안에서 함수를 선언하지 않는다. eslint: [`no-loop-func`](http://eslint.org/docs/rules/no-loop-func.html)

    ```js
    // bad
    let i;
    for (i = 10; i; i--) {
        (function() { 
          return i; 
        })();
    }

    // bad
    while(i) {
        let a = function() {
          return i;
        };
        a();
    }

    // good
    const a = function() {};
    let i;
    for (i = 10; i; i--) {
        a();
    }
    ```
    
**[⬆ back to top](#table-of-contents)**

## Arrow Functions

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [5.1](#5.1) <a name='5.1'></a>arrow function 문법(`=>`)과 비교 연산자 (`<=`, `>=`)를 함께 사용할 경우, 소괄호(`()`)를 이용하여 혼란스럽지 않도록 표현한다. eslint: [`no-confusing-arrow`](http://eslint.org/docs/rules/no-confusing-arrow)

    ```javascript
    // bad
    const itemHeight = item => item.height > 256 ? item.largeSize : item.smallSize;

    // bad
    const itemHeight = (item) => item.height > 256 ? item.largeSize : item.smallSize;

    // good
    const itemHeight = item => (item.height > 256 ? item.largeSize : item.smallSize);

    // good
    const itemHeight = (item) => {
      const { height, largeSize, smallSize } = item;
      return height > 256 ? largeSize : smallSize;
    };
    ```    

**[⬆ back to top](#table-of-contents)**
    
## Classes & Constructors

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [6.1](#6.1) <a name='6.1'></a>상속은 `extends` 를 이용한다. 

    ```javascript
    // bad
    const inherits = require("inherits");
    function PeekableQueue(contents) {
      Queue.apply(this, contents);
    }
    inherits(PeekableQueue, Queue);
    PeekableQueue.prototype.peek = function() {
      return this._queue[0];
    }

    // good
    class PeekableQueue extends Queue {
      peek() {
        return this._queue[0];
      }
    }
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## Modules

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [7.1](#7.1) <a name='7.1'></a>비표준 모듈시스템이 아닌 (`import`/`export`) 를 항상 사용한다.

    ```javascript
    // bad
    const AirbnbStyleGuide = require("./AirbnbStyleGuide");
    module.exports = AirbnbStyleGuide.es6;

    // ok
    import AirbnbStyleGuide from "./AirbnbStyleGuide";
    export default AirbnbStyleGuide.es6;

    // best
    import {es6} from "./AirbnbStyleGuide";
    export default es6;
    ```

- [7.2](#7.2) <a name='7.2'></a>wildcard import 는 이용하지 않는다.

    ```javascript
    // bad
    import * as AirbnbStyleGuide from "./AirbnbStyleGuide";

    // good
    import AirbnbStyleGuide from "./AirbnbStyleGuide";
    ```

- [7.3](#7.3) <a name='7.3'></a>import 문으로부터 직접 export 하지 않는다.

    ```javascript
    // bad
    // filename es6.js
    export {es6 as default} from "./airbnbStyleGuide";

    // good
    // filename es6.js
    import {es6} from "./AirbnbStyleGuide";
    export default es6;
    ```

- [7.4](#7.4) <a name='7.4'></a>import는 중복되지 않게 한 곳에서 import 한다.
eslint: [`no-duplicate-imports`](http://eslint.org/docs/rules/no-duplicate-imports)

    ```javascript
    // bad
    import foo from "foo";
    // … some other imports … //
    import {named1, named2} from "foo";

    // good
    import foo, {named1, named2} from "foo";

    // good
    import foo, {
      named1,
      named2
    } from "foo";
    ```
    
- [7.5](#7.5) <a name='7.5'></a>모든 `import`문은 상위에 위치한다.
eslint: [`import/first`](https://github.com/benmosher/eslint-plugin-import/blob/master/docs/rules/first.md)

    ```javascript
    // bad
    import foo from "foo";
    foo.init();

    import bar from "bar";

    // good
    import foo from "foo";
    import bar from "bar";

    foo.init();
    ```
    
- [7.6](#7.6) <a name='7.6'></a>멀티 라인 imports 문은 배열이나 오브젝트의 literal과 같이 표현한다. [19.1](#19.1)을 참조한다.

    ```javascript
    // good
    import {
      longNameA,
      longNameB,
      longNameC,
      longNameD,
      longNameE,
    } from "path";
    ```

- [7.7](#7.7) <a name='7.7'></a>웹팩 로더 문법 사용에 대해 강제하지 않는다. eslint: [`import/no-webpack-loader-syntax`](https://github.com/benmosher/eslint-plugin-import/blob/master/docs/rules/no-webpack-loader-syntax.md)

    ```javascript
    // good
    import fooSass from 'css!sass!foo.scss';
    import barCss from 'style!css!bar.css';

    // good
    import fooSass from 'foo.scss';
    import barCss from 'bar.css';
    ```

##### - Recommendations

- [7.8](#7.8) <a name='7.8'></a>export가 하나일 경우, default export를 사용한다. 
eslint: [`import/prefer-default-export`](https://github.com/benmosher/eslint-plugin-import/blob/master/docs/rules/prefer-default-export.md)

    ```javascript
    // bad
    export function foo() {}

    // good
    export default function foo() {}
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## Properties

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Recommendations

- [8.1](#8.1) <a name='8.1'></a>프로퍼티에 억세스하는 경우는 점 `.` 을 사용한다. eslint: [`dot-notation`](http://eslint.org/docs/rules/dot-notation.html)

    ```javascript
    const luke = {
      jedi: true,
      age: 28,
    };

    // bad
    const isJedi = luke["jedi"];

    // good
    const isJedi = luke.jedi;
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## Variables

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [9.1](#9.1) <a name='9.1'></a>변수를 선언 할 때는 가급적 `const`나 `let`을 사용한다. 그렇게 하지 않으면 글로벌 변수로 선언된다. eslint: [`no-undef`](http://eslint.org/docs/rules/no-undef) [`prefer-const`](http://eslint.org/docs/rules/prefer-const)

    ```javascript
    // bad
    superPower = new SuperPower();

    // good
    const superPower = new SuperPower();
    ```

- [9.2](#9.2) <a name='9.2'></a>변수 선언은 변수당 하나씩 사용한다. eslint: [`one-var`](http://eslint.org/docs/rules/one-var.html)

    ```javascript
    // bad
    const items = getItems(),
        goSportsTeam = true,
        dragonball = "z";

    // bad
    // (compare to above, and try to spot the mistake)
    const items = getItems(),
        goSportsTeam = true;
        dragonball = "z";

    // good
    const items = getItems();
    const goSportsTeam = true;
    const dragonball = "z";
    ```

- [9.3](#9.3) <a name='9.3'></a> 우선 `const` 를 그룹화하고 다음에 `let` 을 그룹화 한다.

    ```javascript
    // bad
    let i, len, dragonball,
        items = getItems(),
        goSportsTeam = true;

    // bad
    let i;
    const items = getItems();
    let dragonball;
    const goSportsTeam = true;
    let len;

    // good
    const goSportsTeam = true;
    const items = getItems();
    let dragonball;
    let i;
    let length;
    ```

- [9.4](#9.4) <a name='9.4'></a>`let`과 `const`을 사용할 때는 블럭 스코프 이기 때문에, 변수가 사용될 적당한 위치에 변수를 선언한다. 단, `let`과 `const`를 사용할 수 없다면, `var`로 상단에 변수를 선언한다. 

    ```javascript
    // bad
    function foo() {
      var i = 0;
      if (i > 0) {
        var j = 0;
      }
    }

    // good
    function foo() {
      var i = 0;
      var j = 0;
      if (i > 0) {
        j = 0;
      }
    }

    // bad - 불필요한 함수 호출
    function checkName(hasName) {
      const name = getName();

      if (hasName === "test") {
        return false;
      }

      if (name === "test") {
        this.setName('');
        return false;
      }

      return name;
    }

    // good
    function checkName(hasName) {
      if (hasName === "test") {
        return false;
      }

      const name = getName();

      if (name === "test") {
        this.setName("");
        return false;
      }

      return name;
    }
    ```

- [9.5](#9.5) <a name='13.5'></a>여러 개의 변수를 한 줄에 동일한 값으로 설정하지 않는다. eslint [`no-multi-assign`](http://eslint.org/docs/rules/no-multi-assign)

    ```javascript
    // bad
    (function example() {
      let a = b = c = 1;
    }());

    // good
    (function example() {
      let a = 1;
      let b = a;
      let c = a;
    }());
    ```

- [9.6](#9.6) <a name='9.6'></a>++, -- 연산자 사용이 가능하다. 연산자와 피연산자 사이에 공백을 두지 않는다. eslint [`no-plusplus`](http://eslint.org/docs/rules/no-plusplus)

    ```javascript
    // bad
    ++ i;
    i ++;

    // good
    ++i;
    i++;
    ```

**[⬆ back to top](#table-of-contents)**
    
## Comparison Operators & Equality

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [10.1](#10.1) <a name='10.1'></a>`==` 이나 `!=` 보다 `===` 와 `!==` 을 사용한다.

- [10.2](#10.2) <a name='10.2'></a>`if` 문과 같은 조건식은 `ToBoolean` 메서드에 의한 강제형변환으로 평가되어 항상 다음과 같은 심플한 룰을 따른다.
    + **Object** 는 **true** 로 평가된다.
    + **undefined** 는 **false** 로 평가된다.
    + **null** 은 **false** 로 평가된다.
    + **Boolan** 은 **boolean형의 값** 으로 평가된다.
    + **Number** 는 **true** 로 평가된다. 하지만 **+0, -0, or NaN** 의 경우는 **false** 이다.
    + **String** 은 **true** 로 평가된다. 하지만 빈문자 `""` 의 경우는 **false** 이다.

    ```javascript
    if ([0]) {
      // true
      // 배열은 오브젝트이므로 true 로 평가된다.
    }
    ```
 
##### - Recommendations

- [10.3](#10.3) <a name='10.3'></a>`case`, `default` 구문에서 `let`, `const`, `function`, `class`가 사용 되는 경우에는 중괄호(`{}`)를 사용한다. eslint rules: [`no-case-declarations`](http://eslint.org/docs/rules/no-case-declarations.html).

    ```javascript
    // bad
    switch (foo) {
      case 1:
        let x = 1;
        break;
      case 2:
        const y = 2;
        break;
      case 3:
        function f() {
          // ...
        }
        break;
      default:
        class C {}
    }

    // good
    switch (foo) {
      case 1: {
        let x = 1;
        break;
      }
      case 2: {
        const y = 2;
        break;
      }
      case 3: {
        function f() {
          // ...
        }
        break;
      }
      case 4:
        bar();
        break;
      default: {
        class C {}
      }
    }
    ```  
    
**[⬆ back to top](#table-of-contents)**
    
## Blocks

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [11.1](#11.1) <a name='11.1'></a>중괄호(`{}`)는 클래스, 메서드, 제어문의 블럭을 구분한다. 중괄호는 클래스 선언, 메서드 선언, 조건/반복문/제어문,줄의 마지막에서 시작한다.

    ```javascript
    // bad
    const Empty = function()
    {
    }

    // good
    const Empty = function() {
    }

    switch (type) {
      case 0:
        break;
      case 1: {
        break;
      }
      default:
        common();
    }

    if (true) {
      return;
    } else if (false) {
      return;
    } else {
    }
    ```

- [11.2](#11.2) <a name='11.2'></a>조건/반복문/제어문에 중괄호 사용한다. 조건/반복문/제어문이 한줄로 끝이라도 중괄호를 활용한다. eslint: [`brace-style`](http://eslint.org/docs/rules/brace-style.html)

    ```javascript
    // bad
    if (exp == null) return false;
    for (var i in obj) if ( i === "key" ) return obj[i];

    // good
    if (exp == null) {
      return false;
    }

    for (var i in obj) {
      if ( i === "stop" ) {
        return obj[i];
      }
    }
    ```

**[⬆ back to top](#table-of-contents)**

## Comments

> `12.2`, `12.3`, `12.4` 항목을 제외하고는 [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [12.1](#12.1) <a name='12.1'></a>단일행 코멘트에는 `//` 을 사용한다. 코멘트를 추가하고 싶은 코드의 상부에 배치한다. 또한, 코멘트의 앞에 공백을 넣는다.

    ```javascript
    // bad
    const active = true;  // is current tab

    // good
    // is current tab
    const active = true;

    // bad
    function getType() {
      console.log("fetching type...");
      // set the default type to "no type"
      const type = this._type || "no type";

      return type;
    }

    // good
    function getType() {
      console.log("fetching type...");

      // set the default type to "no type"
      const type = this._type || "no type";

      return type;
    }

    // also good
    function getType() {
      // set the default type to "no type"
      const type = this._type || "no type";

      return type;
    }
    ```

- [12.2](#12.2) <a name='12.2'></a>문제를 지적하고 재고를 촉구하는 경우나 문제의 해결책을 제안하는 경우 등, 코멘트의 앞에  `MustFix` 나 `ToDo` 를 붙이는 것으로 다른 개발자가 빠르게 이해할 수 있도록 한다. 이런 것들은 어떤 액션을 따른다는 의미이다.  액션이라는 것은 `MustFix -- 해결이 필요` 또는 `ToDo -- 구현이 필요` 를 뜻한다.

- [12.3](#12.3) <a name='12.3'></a>문제의 해결이 필요하다는 주석으로써 `// MustFix:` 를 사용한다.

    ```javascript
    class Calculator extends Abacus {
      constructor() {
        super();

        // MustFix: total은 글로벌 변수를 사용해야한다.
        total = 0;
      }
    }
    ```

- [12.4](#12.4) <a name='12.4'></a>구현이 필요하다는 주석으로 `// ToDo:` 를 사용한다.

    ```javascript
    class Calculator extends Abacus {
      constructor() {
        super();

        // ToDo: total 은 옵션 파라미터로 설정해야한다.
        this.total = 0;
      }
    }
    ```

**[⬆ back to top](#table-of-contents)**

## Whitespace

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [13.1](#13.1) <a name='13.1'></a>공백은 탭을 사용한다. eslint: [`indent`](http://eslint.org/docs/rules/indent.html)

    ```javascript
    // bad
    function() {
    ∙∙∙∙var name;
    }

    // bad
    function() {
    ∙var name;
    }

    // good
    function() {
      var name;
    }
    ```
    
- [13.2](#13.2) <a name='13.2'></a>문의 앞과 블록의 뒤에는 빈행을 남겨둔다.

    ```javascript
    // bad
    if (foo) {
      return bar;
    }
    return baz;

    // good
    if (foo) {
      return bar;
    }

    return baz;

    // bad
    const obj = {
      foo() {
      },
      bar() {
      },
    };
    return obj;

    // good
    const obj = {
      foo() {
      },

      bar() {
      },
    };

    return obj;

    // bad
    const arr = [
      function foo() {
      },
      function bar() {
      },
    ];
    return arr;

    // good
    const arr = [
      function foo() {
      },

      function bar() {
      },
    ];

    return arr;
    ```
    
- [13.3](#13.3) <a name='13.3'></a>제어구문 (`if` 문이나 `while` 문 등) 의 소괄호(`()`) 앞에는 공백을 1개 넣는다. 함수선언이나 함수호출시 인수리스트의 앞에는 공백을 넣지 않는다.  eslint: [`keyword-spacing`](http://eslint.org/docs/rules/keyword-spacing.html)

    ```javascript
    // bad
    if(isJedi) {
      fight ();
    }

    // good
    if (isJedi) {
      fight();
    }

    // bad
    function fight () {
      console.log ("Swooosh!");
    }

    // good
    function fight() {
      console.log("Swooosh!");
    }
    ```
    
##### - Recommendations
    
- [13.4](#13.4) <a name='13.4'></a>최대 줄 너비는 `100` 이다. 
고해상도 모니터(해상도 1280*1024)사용이 보편화 됨에 따라, 최대 줄 사용 너비는 100자까지 가능하다. eslint: [`max-len`](http://eslint.org/docs/rules/max-len.html)

    ```javascript
    // bad
    const foo = jsonData && jsonData.foo && jsonData.foo.bar && jsonData.foo.bar.baz && jsonData.foo.bar.baz.quux && jsonData.foo.bar.baz.quux.xyzzy;

    // bad
    $.ajax({ method: "POST", url: "https://www.naver.com/", data: { name: "John" } }).done(() => console.log("Congratulations!")).fail(() => console.log("You have failed this city."));

    // good
    const foo = jsonData &&
      jsonData.foo &&
      jsonData.foo.bar &&
      jsonData.foo.bar.baz &&
      jsonData.foo.bar.baz.quux &&
      jsonData.foo.bar.baz.quux.xyzzy;
    ```

- [13.5](#13.5) <a name='13.5'></a>연산식의 경우에는 연산자 후에 줄 바꿈을 한다. 상위 레벨의 깊이에 맞게 들여쓰기를 한다

    ```javascript
    // bad
    const sum = 100 + 200 + 300 
    + 400 + 500 + 600 + 700 + 800;

    // good
    const sum = 100 + 200 + 300 + 
         400 + 500 + 600 + 700 + 800;
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## Commas

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [14.1](#14.1) <a name='14.1'></a>콤마는 뒤에 표기한다. eslint: [`comma-style`](http://eslint.org/docs/rules/comma-style.html)

    ```javascript
    // bad
    const story = [
        once
      , upon
      , aTime
    ];

    // good
    const story = [
      once,
      upon,
      aTime,
    ];

    // bad
    const hero = {
        firstName: "Ada"
      , lastName: "Lovelace"
      , birthYear: 1815
      , superPower: "computers"
    };

    // good
    const hero = {
      firstName: "Ada",
      lastName: "Lovelace",
      birthYear: 1815,
      superPower: "computers"
    };
    ```

- [14.2](#14.2) <a name='14.2'></a>끝에 콤마를 넣는다. eslint: [`comma-dangle`](http://eslint.org/docs/rules/comma-dangle.html)

    ```javascript
    // bad
    const hero = {
      firstName: "Dana",
      lastName: "Scully"
    };

    const heroes = [
      "Batman",
      "Superman"
    ];

    // good
    const hero = {
      firstName: "Dana",
      lastName: "Scully",
    };

    const heroes = [
      "Batman",
      "Superman",
    ];
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## Naming Conventions

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [15.1](#15.1) <a name='15.1'></a>1 문자의 이름은 사용하지 않는다. eslint: [`id-length`](http://eslint.org/docs/rules/id-length)

    ```javascript
    // bad
    function q() {
      // ...stuff...
    }

    // good
    function query() {
      // ..stuff..
    }
    ```

- [15.2](#15.2) <a name='15.2'></a>네임스페이스, 오브젝트, 함수 그리고 인스턴스에는 camelCase를 사용한다. eslint: [`camelcase`](http://eslint.org/docs/rules/camelcase.html)  

    ```javascript
    // bad
    naver.FOO.bar = function() {};
    const OBJEcttsssss = {};
    const this_is_my_object = {};
    function c() {}

    // good
    naver.foo.bar = function() {};
    const thisIsMyObject = {};
    function thisIsMyFunction() {}
    ```

- [15.3](#15.3) <a name='15.3'></a>클래스나 constructor에는 PascalCase 를 사용한다. eslint: [`new-cap`](http://eslint.org/docs/rules/new-cap.html) 

    ```javascript
    // bad
    function user(options) {
      this.name = options.name;
    }

    const bad = new user({
      name: "nope",
    });

    // good
    class User {
      constructor(options) {
        this.name = options.name;
      }
    }

    const good = new User({
      name: "yup",
    });
    ```

- [15.4](#15.4) <a name='15.4'></a>파일을 1개의 `클래스`로 export 하는 경우, 파일명은 클래스명과 완전히 일치시킨다.

    ```javascript
    // file contents
    class CheckBox {
      // ...
    }
    export default CheckBox;

    // in some other file
    // bad
    import CheckBox from "./checkBox";

    // bad
    import CheckBox from "./check_box";

    // good
    import CheckBox from "./CheckBox";
    ```
    
- [15.5](#15.5) <a name='15.5'></a>약어 및 이니셜은 항상 모두 대문자이거나 모두 소문자이어야 한다.

    ```javascript
    // bad
    import SmsContainer from "./containers/SmsContainer";

    // bad
    const HttpRequests = [
      // ...
    ];

    // good
    import SMSContainer from "./containers/SMSContainer";

    // good
    const HTTPRequests = [
      // ...
    ];

    // best
    import TextMessageContainer from "./containers/TextMessageContainer";

    // best
    const Requests = [
      // ...
    ];
    ```
    
- [15.6](#15.6) <a name='15.6'></a>소스의 변수명, 클래스명 등에는 영문 이외의 언어를 사용하지 않는다.

- [15.7](#15.7) <a name='15.7'></a>한글 발음을 그대로 사용하지 않는다.

    ```javascript
    // ''무형자산''이라는 의미의 변수를 선언한 예.
    // bad
    const moohyungJasan;

    // good
    const intangibleAssets;
    ```

- [15.8](#15.8) <a name='15.8'></a>클래스, 메서드 등의 이름에는 특수 문자를 사용하지 않는다. jQuery 변수의 경우 `$`을 사용하는 것은 예외사항으로 한다.

    ```js
    // bad
    funtion $some() {

    }
    ```
    
##### - Recommendations

- [15.9](#15.9) <a name='15.9'></a>Default export가 `함수`일 경우, camelCase를 이용한다. 파일명은 함수명과 동일해야 한다.

    ```javascript
    function makeStyleGuide() {
    }

    export default makeStyleGuide;
    ```

- [15.10](#15.10) <a name='15.10'></a>`Singleton / function library / 빈 오브젝트`를 export 하는 경우에는 PascalCase를 사용한다.

    ```javascript
    const AirbnbStyleGuide = {
      es6: {
      }
    };

    export default AirbnbStyleGuide;
    ```

- [15.11](#15.11) <a name='15.11'></a>상수명은 대문자를 사용하고, 단어와 단어사이는 _로 연결한다.

    ```js
    // bad
    const firefox = 1;
    const is_left = true;

    // good
    const FIREFOX = 1;
    const IS_LEFT = true;
    ```
    
**[⬆ back to top](#table-of-contents)**
    
## jQuery

> [NAVER](https://github.com/naver/eslint-config-naver/edit/master/STYLE_GUIDE.md) 와 동일합니다.

##### - Required

- [16.1](#16.1) <a name='16.1'></a>jQuery오브젝트의 변수는 선두에 `$` 를 부여한다.

    ```javascript
    // bad
    const sidebar = $(".sidebar");

    // good
    const $sidebar = $(".sidebar");

    // good
    const $sidebarBtn = $(".sidebar-btn");
    ```
