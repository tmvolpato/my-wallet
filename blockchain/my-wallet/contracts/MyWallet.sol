// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

//Uncomment this line to use console.log
//import "hardhat/console.sol";

contract MyWallet {
    event Deposit(address indexed from, address to, uint256 amount);
    event Withdraw(address indexed from, address indexed to, uint256 amount);

    mapping(address => uint256) private _balances; //user => balance

    function balanceOf(address account) public view returns (uint256 balance) {
        return _balances[account];
    }

    function deposit() external payable {       
        require(msg.value > 0, "Value must be greater than 0");
        _balances[msg.sender] += msg.value;

        emit Deposit(msg.sender, address(this), msg.value);
    }

    function withdraw(uint256 amount) external {
        require(_balances[msg.sender] >= amount, "Insufficient balance");
        _balances[msg.sender] -= amount;
        payable(msg.sender).transfer(amount);

        emit Withdraw(address(this), msg.sender, amount);
    }
}
