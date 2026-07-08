-- Day 12: Creating an Oracle Stored Procedure to process bank balance transfers securely
-- Topic: PL/SQL Transaction Management & Exception Rollback Blocks

CREATE OR REPLACE PROCEDURE transfer_funds (
    p_sender_id   IN NUMBER,
    p_receiver_id IN NUMBER,
    p_amount      IN NUMBER
) AS
    v_sender_bal NUMBER;
BEGIN
    -- 1. Lock rows explicitly and check sender balance to prevent race conditions
    SELECT balance INTO v_sender_bal FROM accounts WHERE account_id = p_sender_id FOR UPDATE;
    
    IF v_sender_bal < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in sender account.');
    END IF;

    -- 2. Execute balanced atomic changes across data targets
    UPDATE accounts SET balance = balance - p_amount WHERE account_id = p_sender_id;
    UPDATE accounts SET balance = balance + p_amount WHERE account_id = p_receiver_id;

    -- Commit structural updates together if everything completes smoothly
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        -- Immediately revert all database modifications if any step crashes
        ROLLBACK;
        RAISE;
END;
/

-- Benefit: Encapsulating critical transactional workflows directly in a PL/SQL block 
-- removes structural round-trip delays between the backend server and the database engine, 
-- while maintaining atomic data safety.
