package main

import (
	"fmt"
	"sync"
	"time"
)

// Global variables
var customerBalance int = 1000
var bankBalance int = 10000
var mutex sync.Mutex

// Toggle to test with/without mutex
const USE_MUTEX = true // Change to false for Screenshot #1

// Transaction structure for channel-based processing
type Transaction struct {
	Amount     int
	Source     string
	CustomerID string
	Type       string // "withdraw" or "deposit"
}

// ============================================================================
// WITHDRAW FUNCTION
// ============================================================================
func withdraw(amount int, source string, wg *sync.WaitGroup, useMutex bool) {
	defer wg.Done()

	if useMutex {
		mutex.Lock()
		defer mutex.Unlock()
	}

	if customerBalance >= amount {
		fmt.Printf("[%s] Withdrawing $%d...\n", source, amount)
		time.Sleep(100 * time.Millisecond) // Simulate processing

		customerBalance -= amount
		bankBalance += amount

		fmt.Printf("[%s] ✓ Withdrawal successful. Customer: $%d, Bank: $%d\n",
			source, customerBalance, bankBalance)
	} else {
		fmt.Printf("[%s] ✗ Insufficient funds. Balance: $%d\n", source, customerBalance)
	}
}

// ============================================================================
// DEPOSIT FUNCTION
// ============================================================================
func deposit(amount int, source string, wg *sync.WaitGroup, useMutex bool) {
	defer wg.Done()

	if useMutex {
		mutex.Lock()
		defer mutex.Unlock()
	}

	fmt.Printf("[%s] Depositing $%d...\n", source, amount)
	time.Sleep(100 * time.Millisecond) // Simulate processing

	customerBalance += amount
	bankBalance -= amount

	fmt.Printf("[%s] ✓ Deposit successful. Customer: $%d, Bank: $%d\n",
		source, customerBalance, bankBalance)
}

// ============================================================================
// CHANNEL-BASED TRANSACTION PROCESSOR
// ============================================================================
func transactionProcessor(transactionChan <-chan Transaction, done chan<- bool) {
	for transaction := range transactionChan {
		if transaction.Type == "withdraw" {
			if customerBalance >= transaction.Amount {
				fmt.Printf("[%s] Processing withdrawal of $%d for %s...\n",
					transaction.Source, transaction.Amount, transaction.CustomerID)

				customerBalance -= transaction.Amount
				bankBalance += transaction.Amount

				fmt.Printf("[%s] ✓ Withdrawal complete. Customer: $%d, Bank: $%d\n",
					transaction.Source, customerBalance, bankBalance)
			} else {
				fmt.Printf("[%s] ✗ Insufficient funds for %s. Balance: $%d\n",
					transaction.Source, transaction.CustomerID, customerBalance)
			}
		} else if transaction.Type == "deposit" {
			fmt.Printf("[%s] Processing deposit of $%d for %s...\n",
				transaction.Source, transaction.Amount, transaction.CustomerID)

			customerBalance += transaction.Amount
			bankBalance -= transaction.Amount

			fmt.Printf("[%s] ✓ Deposit complete. Customer: $%d, Bank: $%d\n",
				transaction.Source, customerBalance, bankBalance)
		}
		time.Sleep(50 * time.Millisecond)
	}
	done <- true
}

// ============================================================================
// DEMO 1: RACE CONDITION (Without Mutex) - SCREENSHOT #1
// ============================================================================
func demoRaceCondition() {
	customerBalance = 1000
	bankBalance = 10000

	fmt.Println("\n" + "="*70)
	fmt.Println("DEMO 1: RACE CONDITION (Without Mutex)")
	fmt.Println("=" * 70)
	fmt.Printf("Initial Customer Balance: $%d\n\n", customerBalance)

	var wg sync.WaitGroup
	wg.Add(2)

	go withdraw(700, "Phone Transfer", &wg, false) // false = no mutex
	go withdraw(500, "ATM Withdrawal", &wg, false)

	wg.Wait()

	fmt.Printf("\n⚠️  Final Customer Balance: $%d ", customerBalance)
	fmt.Println("(INCORRECT - Race condition occurred!)")
	fmt.Printf("Final Bank Balance: $%d\n", bankBalance)
}

// ============================================================================
// DEMO 2: WITH MUTEX (Correct) - SCREENSHOT #2
// ============================================================================
func demoWithMutex() {
	customerBalance = 1000
	bankBalance = 10000

	fmt.Println("\n" + "="*70)
	fmt.Println("DEMO 2: WITH MUTEX (Correct)")
	fmt.Println("=" * 70)
	fmt.Printf("Initial Customer Balance: $%d\n\n", customerBalance)

	var wg sync.WaitGroup
	wg.Add(2)

	go withdraw(700, "Phone Transfer", &wg, true) // true = use mutex
	go withdraw(500, "ATM Withdrawal", &wg, true)

	wg.Wait()

	fmt.Printf("\n✓ Final Customer Balance: $%d (CORRECT!)\n", customerBalance)
	fmt.Printf("Final Bank Balance: $%d\n", bankBalance)
}

// ============================================================================
// DEMO 3: CHANNEL-BASED PROCESSING - SCREENSHOT #3
// ============================================================================
func demoWithChannels() {
	customerBalance = 1000
	bankBalance = 10000

	fmt.Println("\n" + "="*70)
	fmt.Println("DEMO 3: CHANNEL-BASED PROCESSING (Safe)")
	fmt.Println("=" * 70)
	fmt.Printf("Initial Customer Balance: $%d\n\n", customerBalance)

	transactionChan := make(chan Transaction, 10)
	done := make(chan bool)

	go transactionProcessor(transactionChan, done)

	// Send transactions to channel
	go func() {
		transactionChan <- Transaction{700, "Phone Transfer", "CUST001", "withdraw"}
	}()

	go func() {
		transactionChan <- Transaction{500, "ATM Withdrawal", "CUST001", "withdraw"}
	}()

	time.Sleep(200 * time.Millisecond)
	close(transactionChan)
	<-done

	fmt.Printf("\n✓ Final Customer Balance: $%d\n", customerBalance)
	fmt.Printf("Final Bank Balance: $%d\n", bankBalance)
}

// ============================================================================
// DEMO 4: COMPLETE SYSTEM (Withdraw + Deposit + 3rd Transaction) - SCREENSHOT #4
// ============================================================================
func demoComplete() {
	customerBalance = 1000
	bankBalance = 10000

	fmt.Println("\n" + "="*70)
	fmt.Println("DEMO 4: COMPLETE SYSTEM (Withdraw, Deposit, Online Purchase)")
	fmt.Println("=" * 70)
	fmt.Printf("Initial Customer Balance: $%d\n\n", customerBalance)

	var wg sync.WaitGroup
	wg.Add(4)

	// Three different transaction types
	go withdraw(300, "ATM Withdrawal", &wg, USE_MUTEX)
	go deposit(500, "Salary Deposit", &wg, USE_MUTEX)
	go withdraw(200, "Online Purchase", &wg, USE_MUTEX) // 3rd transaction
	go deposit(150, "Refund", &wg, USE_MUTEX)

	wg.Wait()

	fmt.Printf("\n✓ Final Customer Balance: $%d\n", customerBalance)
	fmt.Printf("Final Bank Balance: $%d\n", bankBalance)
}

// ============================================================================
// DEMO 5: COMPLETE WITH CHANNELS - SCREENSHOT #5
// ============================================================================
func demoCompleteWithChannels() {
	customerBalance = 1000
	bankBalance = 10000

	fmt.Println("\n" + "="*70)
	fmt.Println("DEMO 5: COMPLETE SYSTEM WITH CHANNELS")
	fmt.Println("=" * 70)
	fmt.Printf("Initial Customer Balance: $%d\n\n", customerBalance)

	transactionChan := make(chan Transaction, 10)
	done := make(chan bool)

	go transactionProcessor(transactionChan, done)

	// Send multiple transaction types
	go func() {
		transactionChan <- Transaction{300, "ATM Withdrawal", "CUST001", "withdraw"}
		transactionChan <- Transaction{500, "Salary Deposit", "CUST001", "deposit"}
		transactionChan <- Transaction{200, "Online Purchase", "CUST001", "withdraw"}
		transactionChan <- Transaction{150, "Refund", "CUST001", "deposit"}
	}()

	time.Sleep(500 * time.Millisecond)
	close(transactionChan)
	<-done

	fmt.Printf("\n✓ Final Customer Balance: $%d\n", customerBalance)
	fmt.Printf("Final Bank Balance: $%d\n", bankBalance)
}

// ============================================================================
// MAIN FUNCTION - Runs all demos
// ============================================================================
func main() {
	fmt.Println("\n" + "="*70)
	fmt.Println("GO BANKING SYSTEM - CONCURRENCY LAB")
	fmt.Println("=" * 70)

	// Run all 5 demos
	demoRaceCondition()        // Screenshot #1
	demoWithMutex()            // Screenshot #2
	demoWithChannels()         // Screenshot #3
	demoComplete()             // Screenshot #4
	demoCompleteWithChannels() // Screenshot #5

	// Print observations
	fmt.Println("\n" + "="*70)
	fmt.Println("OBSERVATIONS")
	fmt.Println("=" * 70)
	fmt.Println("1. WITHOUT MUTEX: Race conditions cause incorrect balances")
	fmt.Println("   - Multiple goroutines read balance simultaneously")
	fmt.Println("   - Both may see $1000 and approve withdrawals")
	fmt.Println("   - Result: Overdraft (negative balance)")
	fmt.Println()
	fmt.Println("2. WITH MUTEX: Correct synchronization")
	fmt.Println("   - Only one goroutine accesses balance at a time")
	fmt.Println("   - Second transaction waits for first to complete")
	fmt.Println("   - Result: Correct balance, second transaction rejected")
	fmt.Println()
	fmt.Println("3. WITH CHANNELS: Centralized processing")
	fmt.Println("   - Single goroutine handles all transactions")
	fmt.Println("   - No race conditions possible")
	fmt.Println("   - Mimics real banking systems with centralized ledgers")
	fmt.Println("=" * 70)
}
