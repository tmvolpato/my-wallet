import { loadFixture } from "@nomicfoundation/hardhat-toolbox/network-helpers";
import { expect } from "chai";
import { ethers } from "hardhat";

describe("MyWallet", function () {
  async function deployFixture() {
    const [owner, userOne, userTwo] = await ethers.getSigners();

    const MyWallet = await ethers.getContractFactory("MyWallet");
    const myWallet = await MyWallet.deploy();
    await myWallet.waitForDeployment();

    return { myWallet, owner, userOne, userTwo };
  }

  it("Should deposit tokens correctly and emit Deposit event", async function () {
    const { myWallet, userOne } = await loadFixture(deployFixture);
    const depositAmount = ethers.parseEther("10");

    await expect(myWallet.connect(userOne).deposit({ value: depositAmount }))
      .to.emit(myWallet, "Deposit")
      .withArgs(userOne.address, myWallet, depositAmount);

    expect(await myWallet.balanceOf(userOne.address)).to.equal(depositAmount);
  });

  it("Should withdraw correctly and emit Withdraw event", async function () {
    const { myWallet, userOne } = await loadFixture(deployFixture);
    const depositAmount = ethers.parseEther("10");
    const withdrawAmount = ethers.parseEther("5");

    await myWallet.connect(userOne).deposit({ value: depositAmount });

    await expect(myWallet.connect(userOne).withdraw(withdrawAmount))
      .to.emit(myWallet, "Withdraw")
      .withArgs(myWallet, userOne.address, withdrawAmount);

    expect(await myWallet.balanceOf(userOne.address)).to.equal(
      depositAmount - withdrawAmount
    );
  });

  it("Should handle multiple users' deposits and balances correctly", async function () {
    const { myWallet, userOne, userTwo } = await loadFixture(deployFixture);
    const userOneDepositAmount = ethers.parseEther("10");
    const userTwoDepositAmount = ethers.parseEther("20");

    await myWallet.connect(userOne).deposit({ value: userOneDepositAmount });
    await myWallet.connect(userTwo).deposit({ value: userTwoDepositAmount });

    expect(await myWallet.balanceOf(userOne.address)).to.equal(
      userOneDepositAmount
    );
    expect(await myWallet.balanceOf(userTwo.address)).to.equal(
      userTwoDepositAmount
    );
  });

  it("Should not allow deposits without ether", async function () {
    const { myWallet, userOne } = await loadFixture(deployFixture);
    const depositAmount = ethers.parseEther("0");

    await expect(
      myWallet.connect(userOne).deposit({ value: depositAmount })
    ).to.be.revertedWith("Value must be greater than 0");
  });

  it("Should revert if user tries to withdraw more than they have", async function () {
    const { myWallet, userOne } = await loadFixture(deployFixture);
    const depositAmount = ethers.parseEther("10");
    const withdrawAmount = ethers.parseEther("15");

    await myWallet.connect(userOne).deposit({ value: depositAmount });

    await expect(
      myWallet.connect(userOne).withdraw(withdrawAmount)
    ).to.be.revertedWith("Insufficient balance");
  });
  
});
