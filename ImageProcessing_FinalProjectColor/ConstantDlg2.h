#pragma once


// CConstantDlg2 대화 상자입니다.

class CConstantDlg2 : public CDialog
{
	DECLARE_DYNAMIC(CConstantDlg2)

public:
	CConstantDlg2(CWnd* pParent = NULL);   // 표준 생성자입니다.
	virtual ~CConstantDlg2();

// 대화 상자 데이터입니다.
	enum { IDD = IDD_DIALOG2 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 지원입니다.

	DECLARE_MESSAGE_MAP()
public:
	int xValue;
	int yValue;
};
