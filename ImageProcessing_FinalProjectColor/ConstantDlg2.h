#pragma once


// CConstantDlg2 ��ȭ �����Դϴ�.

class CConstantDlg2 : public CDialog
{
	DECLARE_DYNAMIC(CConstantDlg2)

public:
	CConstantDlg2(CWnd* pParent = NULL);   // ǥ�� �������Դϴ�.
	virtual ~CConstantDlg2();

// ��ȭ ���� �������Դϴ�.
	enum { IDD = IDD_DIALOG2 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV �����Դϴ�.

	DECLARE_MESSAGE_MAP()
public:
	int xValue;
	int yValue;
};
