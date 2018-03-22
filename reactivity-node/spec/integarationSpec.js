var request = require("superagent");

var baseUrl = "http://localhost:3000";
var calculateUrl = baseUrl + "/calculate";

describe("Reactivity", function(){

    function defineTests(sync){
        function testCalculate(description, payload, expectedResult) {        
            it(description, function(done) {
                let currentUrl = calculateUrl + (sync ? '?sync=true': '');
                console.log("Sending", currentUrl, payload);
                request
                    .post(currentUrl)
                    .send(payload)
                    .then(function(res) {
                       expect(res.status).toBe(200);  
                       expect(res.body).toEqual(expectedResult);            
                       done();
                    })
                    .catch(function(err){
                        console.log(err);
                        fail(err);
                    });
            });
        }

        describe("Calculate", function(){
            testCalculate("Empty calculation", [], []);

            describe("Simple", function(){
                testCalculate("Simple ADD 1",
                    [{
                        operation: "ADD",
                        leftValue: 1,
                        rightValue: 1
                    }],
                    [
                        2
                    ]);
                testCalculate("Simple MULTIPLY 1",
                    [{
                        operation: "MULTIPLY",
                        leftValue: 5,
                        rightValue: 5
                    }],
                    [
                        25
                    ]);
                testCalculate("Simple invalid operation",
                    [{
                        operation: "INVALID"
                    }],
                    [
                        0
                    ]);
                testCalculate("Simple READFILE 1 ",
                    [{
                        operation: "READFILE",
                        leftValue: 2
                    }],
                    [
                        3
                    ]);
                testCalculate("Simple ADD 2",
                    [{
                        operation: "ADD",
                        leftValue: 5,
                        rightValue: 5
                    }],
                    [
                        10
                    ]);
                testCalculate("Simple MULTIPLY 2",
                    [{
                        operation: "MULTIPLY",
                        leftValue: 10,
                        rightValue: 5
                    }],
                    [
                        50
                    ]);
                testCalculate("Simple READFILE 2",
                    [{
                        operation: "READFILE",
                        leftValue: 1
                    }],
                    [
                        6
                    ]);
                testCalculate("Simple PI",
                    [{
                        operation: "PI",
                        leftValue: 1
                    },{
                        operation: "PI",
                        leftValue: 2
                    },{
                        operation: "PI",
                        leftValue: 3
                    },{
                        operation: "PI",
                        leftValue: 100
                    },{
                        operation: "PI",
                        leftValue: 300
                    }],
                    [
                        4,
                        2.666666666666667,
                        3.466666666666667,
                        3.1315929035585537,
                        3.1382593295155914
                    ]);
                testCalculate("Simple READDB 1",
                    [{
                        operation: "READDB",
                        leftValue: 1
                    }],
                    [
                        10
                    ]);
                testCalculate("Simple READDB 2",
                    [{
                        operation: "READDB",
                        leftValue: 2
                    }],
                    [
                    20
                    ]);
                testCalculate("Simple READDB invalid",
                    [{
                        operation: "READDB",
                        leftValue: 4
                    }],
                    [
                        0
                    ]);
                });    

            describe("Multiple", function(){
                testCalculate("Multiple ADD",
                    [{
                        operation: "ADD",
                        leftValue: 4,
                        rightValue: 4
                    },{
                        operation: "ADD",
                        leftValue: 5,
                        rightValue: 5
                    },{
                        operation: "ADD",
                        leftValue: 6,
                        rightValue: 6
                    }],
                    [
                        8,
                        10,
                        12
                    ]);
                testCalculate("Multiple MULTIPLY",
                    [{
                        operation: "MULTIPLY",
                        leftValue: 4,
                        rightValue: 4
                    },{
                        operation: "MULTIPLY",
                        leftValue: 5,
                        rightValue: 5
                    },{
                        operation: "MULTIPLY",
                        leftValue: 6,
                        rightValue: 6
                    }],
                    [
                        16,
                        25,
                        36
                    ]);
                testCalculate("Multiple READFILE",
                    [{
                        operation: "READFILE",
                        leftValue: 1
                    },{
                        operation: "READFILE",
                        leftValue: 2
                    },{
                        operation: "READFILE",
                        leftValue: 3
                    }],
                    [
                        6,
                        3,
                        1
                    ]);
                testCalculate("Multiple Mixed",
                    [{
                        operation: "ADD",
                        leftValue: 5,
                        rightValue: 5
                    },{
                        operation: "MULTIPLY",
                        leftValue: 5,
                        rightValue: 5
                    },{
                        operation: "READFILE",
                        leftValue: 2
                    }],
                    [
                        10,
                        25,
                        3
                    ]);
            });    

            describe("Operation as operand", function(){
                testCalculate("Operation ADD",
                    [{
                        operation: "ADD",
                        leftOperation: {
                            operation: "ADD",
                            leftValue: 3,
                            rightValue: 3
                        },
                        rightOperation: {
                            operation: "ADD",
                            leftValue: 2,
                            rightValue: 2
                        }
                    }],
                    [
                        10
                    ]);
                testCalculate("Operation MULTIPLY",
                    [{
                        operation: "MULTIPLY",
                        leftOperation: {
                            operation: "MULTIPLY",
                            leftValue: 3,
                            rightValue: 3
                        },
                        rightOperation: {
                            operation: "MULTIPLY",
                            leftValue: 2,
                            rightValue: 2
                        }
                    }],
                    [
                        36
                    ]);
                testCalculate("Operation READFILE",
                    [{
                        operation: "READFILE",
                        leftOperation: {
                            operation: "READFILE",
                            leftValue: 3
                        }
                    }],
                    [
                        6
                    ]);
                testCalculate("Complex Operation 1",
                    [{
                        operation: "ADD",
                        leftOperation: {
                            operation: "READFILE",
                            leftValue: 1
                        },
                        rightOperation: {
                            operation: "MULTIPLY",
                            leftValue: 2,
                            rightValue: 2
                        }
                    }],
                    [
                        10
                    ]);
                testCalculate("Complex Operation 1",
                    [{
                        operation: "ADD",
                        leftOperation: {
                            operation: "ADD",
                            leftValue: 1,
                            rightOperation: {
                                operation: "ADD",
                                leftOperation: {
                                    operation: "ADD",
                                    leftValue: 1,
                                    rightValue: 5
                                },
                                rightOperation: {
                                    operation: "MULTIPLY",
                                    leftValue: 2,
                                    rightValue: 2
                                }
                            }
                        },
                        rightOperation: {
                            operation: "MULTIPLY",
                            leftValue: 2,
                            rightValue: 2
                        }
                    }],
                    [
                        15
                    ]);
            });
        });    
    }

    defineTests(false);
    defineTests(true);
});